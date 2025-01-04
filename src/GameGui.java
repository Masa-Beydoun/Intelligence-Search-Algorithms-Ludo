import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class GameGui extends JFrame {
    Icon[] nerdImages = {
            new ImageIcon("1.png"),
            new ImageIcon("2.png"),
            new ImageIcon("3.png"),
            new ImageIcon("4.png"),
            new ImageIcon("5.png"),
            new ImageIcon("6.png"),
    };
    State state;
    JButton[][] buttons;
    JPanel game, southPanel;
    JButton nerdButton;
    JLabel turnLabel;
    int ran = 0;

    GameGui(State state) {
        this.state = state;
        createGrid();
        refresh_stones();

        southPanel = new JPanel(new FlowLayout());
        nerdButton = new JButton();
        nerdButton.setPreferredSize(new Dimension(40, 40));

        turnLabel = new JLabel("NEXT Turn: " + (state.turn));

        southPanel.add(nerdButton);
        southPanel.add(turnLabel);

        nerdButton.addActionListener(e -> {
            ran = state.getNerdNumber();
            changeNerd(ran);

        });

        this.add(southPanel, BorderLayout.SOUTH);
        this.add(game, BorderLayout.CENTER);

        this.setBounds(200, 0, 15 * 40, 15 * 40 + 50);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }



    public void createGrid() {
        new State();
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
                buttons[i][j].addActionListener(e -> movement(finalI, finalJ));

                if ((i == 7 && j >= 1 && j <= 5) || (i == 6 && j == 1)) buttons[i][j].setBackground(Color.red);
                else if ((i == 7 && j >= 9 && j <= 13) || (i == 8 && j == 13))
                    buttons[i][j].setBackground(Color.YELLOW);
                else if ((j == 7 && i >= 1 && i <= 5) || (i == 1 && j == 8)) buttons[i][j].setBackground(Color.green);
                else if ((j == 7 && i >= 9 && i <= 13) || (i == 13 && j == 6)) buttons[i][j].setBackground(Color.blue);
                    //////////////////////////////////////////////////////////////
                else if ((i == 1 && (j == 1 || j == 4)) || (i == 4 && (j == 1 | j == 4)) || (i == 8 && j == 2))
                    buttons[i][j].setBackground(Color.red);
                else if ((i == 0 && j <= 5) || (i <= 5 && j == 0) || (i == 5 && j <= 5) || (i <= 5 && j == 5))
                    buttons[i][j].setBackground(new Color(100, 0, 40));
                    //////////////////////////////////////////////////////////////
                else if ((i == 1 && (j == 13 || j == 10)) || (i == 4 && (j == 13 || j == 10)) || (i == 2 && j == 6))
                    buttons[i][j].setBackground(Color.green);
                else if (((i == 0 || i == 5) && j >= 9) || (i <= 5 && (j == 9 || j == 14)))
                    buttons[i][j].setBackground(new Color(0, 100, 0));
                    //////////////////////////////////////////////////////////////
                else if ((i == 10 && (j == 1 || j == 4)) || (i == 13 && (j == 1 || j == 4)) || (i == 12 && j == 8))
                    buttons[i][j].setBackground(Color.blue);
                else if ((i == 14 && j <= 5) || (i == 9 && j <= 5) || (i >= 9 && j == 0) || (i >= 10 && j == 5))
                    buttons[i][j].setBackground(new Color(40, 0, 100));
                    //////////////////////////////////////////////////////////////
                else if ((i == 13 && (j == 13 || j == 10)) || (i == 10 && (j == 10 || j == 13)) || (i == 6 && j == 12))
                    buttons[i][j].setBackground(Color.yellow);
                else if ((i == 14 && j >= 9) || (i == 9 && j >= 9) || (i >= 9 && j == 14) || (i >= 9 && j == 9))
                    buttons[i][j].setBackground(new Color(150, 150, 0));
                    //////////////////////////////////////////////////////////////

                else if (i >= 6 && i <= 8 && j >= 6 && j <= 8) buttons[i][j].setBackground(Color.black);
                else buttons[i][j].setBackground(Color.WHITE);

                if (((i >= 6 && i <= 8) || (j >= 6 && j <= 8)) && !(i >= 6 && i <= 8 && j >= 6 && j <= 8))
                    buttons[i][j].setBorder(new LineBorder(Color.GRAY, 4));
                buttons[i][j].setFocusable(false);
                game.add(buttons[i][j]);
            }

        }
    }

    public void movement(int i, int j) {
        if (!state.checkExist(i, j)) return;
        for(Stone s : state.stones){
            if(s.i == i && s.j == j){
                state.move(s.id, ran);
                refresh_stones();
                return;
            }
        }
    }

    public void changeNerd(int num) {
        nerdButton.setBackground(Color.GRAY);
        nerdButton.setIcon(null);
        nerdButton.setIcon(nerdImages[num - 1]);
    }

    public void refresh_stones() {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                buttons[i][j].setText("");
            }
        }
        for (Stone s : state.stones) {
            StringBuilder text = new StringBuilder();
            text.append(s.id);
            if (!buttons[s.i][s.j].getText().isEmpty())
                text.append(s.id);
            buttons[s.i][s.j].setText(text.toString());
        }
    }




}
