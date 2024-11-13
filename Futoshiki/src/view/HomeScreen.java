package view;

import controller.ManageSettings;
import model.Settings;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Set;

public class HomeScreen {
    private JPanel HomeScreenPanel;
    private JButton playFutoshikiButton;
    private JButton settingsButton;
    private JButton top10Button;
    private JButton aboutUsButton;
    private JButton helpButton;
    private Settings settings;

    public HomeScreen() {

        JFrame frame = new JFrame("Home Screen");
        frame.setContentPane(HomeScreenPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 600);
        frame.setResizable(false);
        frame.setVisible(true);

        try {
            settings = ManageSettings.getSettings();
        }
        catch (IOException | ParseException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "There is an issue with the settings file", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }

        playFutoshikiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new GamePanel(settings);
            }
        });
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new SettingsScreen(settings);
            }
        });
        top10Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new TopScreen();
            }
        });
        aboutUsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new AboutUs();
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
