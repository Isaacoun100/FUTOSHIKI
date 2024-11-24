package view;

import model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HelpScreen {
    private JButton returnToHomeButton;
    private JPanel HelpScreenPanel;

    public HelpScreen( User user ) {

        JFrame frame = new JFrame("Help");
        frame.setContentPane(HelpScreenPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 600);
        frame.setResizable(false);
        frame.setVisible(true);

        returnToHomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new HomeScreen( user );
            }
        });
    }
}
