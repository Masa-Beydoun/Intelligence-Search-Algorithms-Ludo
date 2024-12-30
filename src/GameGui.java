import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class GameGui extends JFrame {
    static Icon Image1 = new ImageIcon("1.png");
    static Icon Image2 = new ImageIcon("2.png");
    static Icon Image3 = new ImageIcon("3.png");
    static Icon Image4 = new ImageIcon("4.png");
    static Icon Image5 = new ImageIcon("5.png");
    static Icon Image6 = new ImageIcon("6.png");
    State state;
    JButton[][] buttons;
    JPanel game, southNerdPanel, northNerdPanel;
    JButton southNerd, northNerd;
    int ran = 0;

    GameGui(State state) {
        this.state = state;
        createGrid();
        refresh_stones();

        game.setBounds(0, 40, 15 * 40, 15 * 40);
        northNerdPanel = new JPanel(new FlowLayout());
        northNerdPanel.setBounds(20, 0, 15 * 40, 20);
        southNerdPanel = new JPanel(new FlowLayout());
        southNerdPanel.setBounds(15 * 50 + 20, 0, 15 * 50, 20);
        southNerd = new JButton();
        southNerd.setBackground(Color.white);
        southNerd.setBounds(15 * 15, 0, 20, 20);
        southNerd.setSize(20, 20);
        northNerd = new JButton();
        northNerd.setBackground(Color.GRAY);
        northNerd.setSize(20, 20);
        northNerdPanel.add(northNerd);
        southNerdPanel.add(southNerd);
        northNerd.addActionListener(e -> {
            if (state.turn == 1) {
//                System.out.println(state);
                ran = state.getNerdNumber();
                changeNerd(ran, false);
            }
        });
        southNerd.addActionListener(e -> {
            if (state.turn == 0) {
                ran = state.getNerdNumber();
                changeNerd(ran, true);
            }
        });
        this.add(northNerdPanel, BorderLayout.NORTH);
        this.add(southNerdPanel, BorderLayout.SOUTH);
        this.add(game, BorderLayout.CENTER);
        this.setBounds(200, 0, 15 * 40, 15 * 40 + 20 * 2);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    //TODO
    public void movement(int i, int j) {
        if (!state.checkExist(i, j)) return;
        if (!buttons[i][j].getText().equals("")) {
            int x = Integer.parseInt(buttons[i][j].getText());
            //if the cell has more than one stone
            while (x > 8) x /= 10;
            state.move(x, ran);
            refresh_stones();
        }
    }


    public void createGrid() {
        new State();
        this.setLayout(new BorderLayout());
        game = new JPanel(new GridLayout(16, 16));
        buttons = new JButton[16][16];

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                buttons[i][j] = new JButton();
                if(i == 15 ){
                    buttons[i][j].setText(String.valueOf(j));
                    game.add(buttons[i][j]);
                    continue;
                }
                if(j == 15){
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

    //this function for changing the nerd between the two players
    public void changeNerd(int ran, boolean x) {
        if (x) {
            northNerd.setBackground(Color.GRAY);
            northNerd.setIcon(null);
        } else {
            southNerd.setBackground(Color.GRAY);
            southNerd.setIcon(null);
        }
        switch (ran) {
            case 1: {
                if (x)
                    southNerd.setIcon(Image1);
                else
                    northNerd.setIcon(Image1);
                break;
            }
            case 2: {
                if (x)
                    southNerd.setIcon(Image2);

                else
                    northNerd.setIcon(Image2);

                break;
            }

            case 3: {
                if (x)
                    southNerd.setIcon(Image3);

                else
                    northNerd.setIcon(Image3);

                break;
            }

            case 4: {
                if (x)
                    southNerd.setIcon(Image4);

                else
                    northNerd.setIcon(Image4);

                break;
            }
            case 5: {
                if (x)
                    southNerd.setIcon(Image5);

                else
                    northNerd.setIcon(Image5);

                break;
            }
            case 6: {
                if (x) {
                    northNerd.setBackground(Color.GRAY);
                    northNerd.setIcon(null);
                    southNerd.setIcon(Image6);
                } else {
                    southNerd.setBackground(Color.GRAY);
                    southNerd.setIcon(null);
                    northNerd.setIcon(Image6);
                }
                break;
            }
            default:
                break;
        }
    }

    public void refresh_stones() {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                buttons[i][j].setText("");
            }
        }
        //add the number of stones to the grid
        for (Stone s : state.stones) {
            String text = Integer.toString(s.id);
            if (!buttons[s.i][s.j].getText().isEmpty())
                text += buttons[s.i][s.j].getText();
            buttons[s.i][s.j].setText(text);
        }
    }
}
