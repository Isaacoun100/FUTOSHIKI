package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartScreen {
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton playButton;
    private JButton continueAnonymouslyButton;
    private JPanel startScreenPanel;

    public StartScreen() {

        JFrame frame = new JFrame("Start Screen");
        frame.setContentPane(startScreenPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setResizable(false);
        frame.setVisible(true);

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        continueAnonymouslyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new HomeScreen();
            }
        });
    }
}
