package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeScreen {
    private JPanel HomeScreenPanel;
    private JButton playFutoshikiButton;
    private JButton settingsButton;
    private JButton top10Button;
    private JButton aboutUsButton;
    private JButton helpButton;

    public HomeScreen() {

        JFrame frame = new JFrame("Home Screen");
        frame.setContentPane(HomeScreenPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 600);
        frame.setResizable(false);
        frame.setVisible(true);

        playFutoshikiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        top10Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        aboutUsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new HelpScreen();
            }
        });
    }
}
