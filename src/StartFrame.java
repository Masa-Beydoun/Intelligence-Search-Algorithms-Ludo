import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartFrame {
    private JPanel panel1;
    private JButton userButton;
    private JButton advancedButton;
    private JButton simpleButton;

    StartFrame(State state){
        userButton.addActionListener(e -> new GameGui(state,"user"));
        advancedButton.addActionListener(e -> new GameGui(state,"advanced"));

        simpleButton.addActionListener(e -> new GameGui(state,"simple"));
    }

}
