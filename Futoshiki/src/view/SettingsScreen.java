package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsScreen {
    private JPanel SettingsPanel;
    private JComboBox sizeComboBox;
    private JButton returnToPreviousMenuButton;
    private JButton saveChangesButton;
    private JComboBox difficultyComboBox;
    private JComboBox multilevelComboBox;
    private JComboBox stopwatchComboBox;

    public SettingsScreen() {

        JFrame frame = new JFrame("Add Admin");
        frame.setContentPane(SettingsPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 600);
        frame.setResizable(false);
        frame.setVisible(true);

        returnToPreviousMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new HomeScreen();
            }
        });
        saveChangesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
    }
}
