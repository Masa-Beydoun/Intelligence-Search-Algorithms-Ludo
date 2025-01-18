import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameGui extends JFrame {

    List<State> visited = new ArrayList<State>();
    Icon[] nerdImages = {
            new ImageIcon("1.png"),
            new ImageIcon("2.png"),
            new ImageIcon("3.png"),
            new ImageIcon("4.png"),
            new ImageIcon("5.png"),
            new ImageIcon("6.png"),
    };
    Icon[] stonesImages = {
            new ImageIcon("red.png"),
            new ImageIcon("green.png"),
            new ImageIcon("yellow.png"),
            new ImageIcon("blue.png"),
    };
    State state;
    JButton[][] buttons;
    JPanel game, southPanel;
    JButton nerdButton = new JButton();
    JLabel turnLabel;
    int ran = 0;

    GameGui(State state) {
        this.state = state;
        createGrid();
        refreshStones();
        southPanel = new JPanel(new FlowLayout());
        nerdButton.setPreferredSize(new Dimension(40, 40));
        turnLabel = new JLabel("NEXT Turn: " + (state.turn));
        southPanel.add(nerdButton);
        southPanel.add(turnLabel);
        nerdButton.addActionListener(e -> nerdActionListener());
        this.add(southPanel, BorderLayout.SOUTH);
        this.add(game, BorderLayout.CENTER);
        this.setBounds(0, 0, 15 * 52, 15 * 50 + 50);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void nerdActionListener() {
        if (!state.played) return;
        ran = state.getNerdNumber();
        nerdButton.setIcon(nerdImages[ran - 1]);
        if (state.played) {
            turnLabel.setText("NEXT Turn: " + (state.turn));
        }
    }

    public void refreshStones() {
        System.out.println("refresh stones");
        System.out.println(state);
        System.out.println();
        System.out.println();
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setIcon(null);
            }
        }

        for (Player player : state.players) {
            for (Stone s : player.stones) {
               buttons[s.position.i][s.position.j].setIcon(stonesImages[player.playerID]);
            }
        }

    }

    public void createGrid() {
        this.setLayout(new BorderLayout());
        game = new JPanel(new GridLayout(16, 16));
        buttons = new JButton[16][16];

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                buttons[i][j] = new JButton();
                if (i == 15) {
                    buttons[i][j].setText(String.valueOf(j));
                    game.add(buttons[i][j]);
                    continue;
                }
                if (j == 15) {
                    buttons[i][j].setText(String.valueOf(i));
                    game.add(buttons[i][j]);
                    continue;
                }
                buttons[i][j].setText("");
                int finalI = i;
                int finalJ = j;
                buttons[i][j].addActionListener(e -> moveListener(finalI, finalJ));

                if ((i == 7 && j >= 1 && j <= 5) || (i == 6 && j == 1)) buttons[i][j].setBackground(Color.red);
                else if ((i == 7 && j >= 9 && j <= 13) || (i == 8 && j == 13))
                    buttons[i][j].setBackground(Color.YELLOW);
                else if ((j == 7 && i >= 1 && i <= 5) || (i == 1 && j == 8)) buttons[i][j].setBackground(Color.green);
                else if ((j == 7 && i >= 9 && i <= 13) || (i == 13 && j == 6)) buttons[i][j].setBackground(Color.blue);
                    //////////////////////////////////////////////////////////////
                else if (i == 8 && j == 2)
                    buttons[i][j].setBackground(Color.red);
                else if ((i == 0 && j <= 5) || (i <= 5 && j == 0) || (i == 5 && j <= 5) || (i <= 5 && j == 5))
                    buttons[i][j].setBackground(new Color(100, 0, 40));
                    ////////////////////////////////////////////////////////////
                else if (i == 2 && j == 6)
                    buttons[i][j].setBackground(Color.green);
                else if (((i == 0 || i == 5) && j >= 9) || (i <= 5 && (j == 9 || j == 14)))
                    buttons[i][j].setBackground(new Color(0, 100, 0));
                    //////////////////////////////////////////////////////////
                else if (i == 12 && j == 8)
                    buttons[i][j].setBackground(Color.blue);
                else if ((i == 14 && j <= 5) || (i == 9 && j <= 5) || (i >= 9 && j == 0) || (i >= 10 && j == 5))
                    buttons[i][j].setBackground(new Color(40, 0, 100));
                    ////////////////////////////////////////////////////////////
                else if (i == 6 && j == 12)
                    buttons[i][j].setBackground(Color.yellow);
                else if ((i == 14 && j >= 9) || (i == 9 && j >= 9) || (i >= 9 && j == 14) || (i >= 9 && j == 9))
                    buttons[i][j].setBackground(new Color(150, 150, 0));
                    ////////////////////////////////////////////////////////////

                else if (i >= 6 && i <= 8 && j >= 6 && j <= 8) buttons[i][j].setBackground(Color.black);
                else buttons[i][j].setBackground(Color.WHITE);

                if (((i >= 6 && i <= 8) || (j >= 6 && j <= 8)) && !(i >= 6 && i <= 8 && j >= 6 && j <= 8))
                    buttons[i][j].setBorder(new LineBorder(Color.GRAY, 4));
                buttons[i][j].setFocusable(false);
                game.add(buttons[i][j]);
            }

        }
    }

    public void moveListener(int i, int j) {
        System.out.println(state);
        if (state.played) {
            System.out.println("already did the move");
            System.out.println();
            return;
        }
        System.out.println("cell in " + i + " " + j + " have been clicked");

        List<Stone> s = state.players.get(state.turn).getStoneInPlace(i, j);
        if (s.isEmpty()) return;
        System.out.println("stone in place " + s);
        State state1 = state.move(s.get(0).id, ran);
        if (state1 == null) return;
        System.out.println("state 1 is played : " + state1.played);
        if (!state1.played) return;

        this.state = new State(ran);
        state.players = List.copyOf(state1.players);
        state.turn = state1.turn;


        this.state.played = true;

        System.out.println("the move have been processed in GameGui class");

        turnLabel.setText("NEXT Turn: " + (state.turn));

        refreshStones();
    }
}

