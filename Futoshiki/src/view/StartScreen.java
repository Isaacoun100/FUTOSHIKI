package view;

import controller.ManageUser;
import model.User;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class StartScreen {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton playButton;
    private JButton continueAnonymouslyButton;
    private JPanel startScreenPanel;
    private JButton createANewUserButton;

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

                try {
                    User user = ManageUser.signIn(
                            usernameField.getText(),
                            passwordField.getText()
                    );

                    if(user != null) {
                        frame.dispose();
                        new HomeScreen(user);
                    }
                    else {
                        JOptionPane.showMessageDialog(frame, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
                        passwordField.setText("");
                    }


                } catch (IOException | ParseException ex) {
                    JOptionPane.showMessageDialog(frame, "Cannot find the users file");
                    throw new RuntimeException(ex);
                }

            }
        });
        continueAnonymouslyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new HomeScreen(null);
            }
        });
        createANewUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new NewUser();
            }
        });
    }
}
