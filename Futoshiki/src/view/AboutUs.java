package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AboutUs {
    private JButton returnToMainMenuButton;
    private JPanel AboutUsPanel;

    public AboutUs() {

        JFrame frame = new JFrame("Home Screen");
        frame.setContentPane(AboutUsPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 600);
        frame.setResizable(false);
        frame.setVisible(true);

        returnToMainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new HomeScreen();
            }
        });
    }
}
