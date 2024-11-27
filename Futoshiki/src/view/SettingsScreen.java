package view;

import controller.ManageSettings;
import controller.ManageUser;
import model.Settings;
import model.User;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.String.valueOf;

public class SettingsScreen {
    private JPanel SettingsPanel;
    private JComboBox sizeComboBox;
    private JButton returnToPreviousMenuButton;
    private JButton saveChangesButton;
    private JComboBox difficultyComboBox;
    private JComboBox multilevelComboBox;
    private JComboBox timeComboBox;
    private JButton defaultButton;
    private JComboBox sideComboBox;

    public SettingsScreen(User user) {

        JFrame frame = new JFrame("Add Admin");
        frame.setContentPane(SettingsPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 600);
        frame.setResizable(false);
        frame.setVisible(true);

        setValues(user.getSettings());

        returnToPreviousMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new HomeScreen(user);
            }
        });
        saveChangesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    int choice = JOptionPane.showConfirmDialog(
                            null,
                            "Changing the settings will delete your progress, do you want to continue?",
                            "Confirmation",
                            JOptionPane.YES_NO_CANCEL_OPTION
                    );

                    if (choice == JOptionPane.YES_OPTION) {
                        user.setSettings( new Settings(
                                sizeComboBox.getSelectedIndex()+3,
                                valueOf(difficultyComboBox.getSelectedItem()),
                                Boolean.parseBoolean(valueOf(multilevelComboBox.getSelectedItem())),
                                valueOf(timeComboBox.getSelectedItem()),
                                valueOf(sideComboBox.getSelectedItem())
                        ));

                        user.setMatrix( new int[sizeComboBox.getSelectedIndex()+3][sizeComboBox.getSelectedIndex()+3] );
                        user.setConstrains( new ArrayList<>() );
                        user.setValues( new ArrayList<>() );

                        ManageSettings.setSettings(user.getSettings());

                        if(!user.getUsername().isEmpty()){
                            ManageUser.setUser(user);
                        }

                    }



                }
                catch (IOException | ParseException ex) {
                    System.out.println("Can't assign the values");
                    throw new RuntimeException(ex);
                }

            }
        });
        defaultButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int choice = JOptionPane.showConfirmDialog(
                        null,
                        "Changing the settings will delete your progress, do you want to continue?",
                        "Confirmation",
                        JOptionPane.YES_NO_CANCEL_OPTION
                );

                if (choice == JOptionPane.YES_OPTION) {

                    try {
                        Settings settings = new Settings(
                                3,
                                "Easy",
                                false,
                                "Stopwatch",
                                "Right");

                        user.setMatrix( new int[sizeComboBox.getSelectedIndex()+3][sizeComboBox.getSelectedIndex()+3] );
                        user.setConstrains( new ArrayList<>() );
                        user.setValues( new ArrayList<>() );
                        user.setSettings(settings);
                        ManageSettings.setSettings(settings);
                        setValues(settings);
                    }
                    catch (IOException | ParseException ex) {
                        throw new RuntimeException(ex);
                    }

                }

            }
        });
    }

    /**
     * I wanted to feel like a smart ass, so I overloaded the method cause
     * also I'm lazy, so this method receives the Settings value and it assigns
     * it to the values in the screen
     * @param settings
     */
    private void setValues(Settings settings){
        sizeComboBox.setSelectedItem(settings.getSize()+" by "+settings.getSize());
        difficultyComboBox.setSelectedItem(settings.getDifficulty());
        multilevelComboBox.setSelectedItem( String.valueOf(settings.isMultilevel()) );
        timeComboBox.setSelectedItem(settings.getClock());
        sideComboBox.setSelectedItem(settings.getSide());
    }

}
