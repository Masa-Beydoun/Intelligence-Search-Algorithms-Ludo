import javax.swing.*;

public class StartFrame extends JFrame{
    private JPanel panel1;
    private JButton userButton;
    private JButton advancedButton;
    private JButton simpleButton;
    private JButton simpleAlgorithmButton;

    StartFrame(State state){
        userButton.addActionListener(e -> {
            new GameGui(state, "user");
            dispose();
        });
        advancedButton.addActionListener(e -> {
            new GameGui(state, "advanced");
            dispose();
        });
        simpleButton.addActionListener(e -> {
            new GameGui(state, "simple");
            dispose();
        });

        simpleAlgorithmButton.addActionListener(e -> {
            new GameGui(state, "simpleAlgorithm");
            dispose();
        });
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Start Game");
        this.setSize(500,500);
        this.setVisible(true);
        this.add(panel1);
    }

}
