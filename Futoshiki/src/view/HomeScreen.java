package view;

import controller.ManageSettings;
import model.Settings;
import model.User;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class HomeScreen {
    private JPanel HomeScreenPanel;
    private JButton playFutoshikiButton;
    private JButton settingsButton;
    private JButton top10Button;
    private JButton aboutUsButton;
    private JButton helpButton;
    private User user;

    public HomeScreen( User user ) {

        JFrame frame = new JFrame("Home Screen");
        frame.setContentPane(HomeScreenPanel);
        frame.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
        frame.setSize(700, 600);
        frame.setResizable(false);
        frame.setVisible(true);

        try {

            if(user == null){
                Settings settings = ManageSettings.getSettings();
                user = new User(
                        settings,
                        "",
                        new int[settings.getSize()][settings.getSize()],
                        new ArrayList<>(),
                        ""
                );
            }

            this.user = user;

        }
        catch (IOException | ParseException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "There is an issue with the settings file", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }

        User finalUser = user;

        playFutoshikiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new GamePanel(finalUser);
            }
        });
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new SettingsScreen(finalUser);
            }
        });
        top10Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new TopScreen(finalUser);
            }
        });
        aboutUsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new AboutUs(finalUser);
            }
        });
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new HelpScreen(finalUser);
            }
        });
    }

}
