package view;

import controller.ManageUser;
import model.Settings;
import model.User;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class NewUser {
    private JButton returnToLogInButton;
    private JButton createNewUserButton;
    private JTextField usernameTextField;
    private JPasswordField passwordField;
    private JPanel newUserPanel;

    public NewUser() {

        JFrame frame = new JFrame("Start Screen");
        frame.setContentPane(newUserPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setResizable(false);
        frame.setVisible(true);

        returnToLogInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new StartScreen();
            }
        });


        createNewUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(usernameTextField.getText().length()>5 && !passwordField.getText().isEmpty()) {
                    try {
                        if(ManageUser.createUser(
                                new User( new Settings(
                                        3,
                                        "Easy",
                                        false,
                                        "No",
                                        "Right"
                                       ),
                                        passwordField.getText(),
                                        new int[3][3],
                                        new ArrayList<>(),
                                        new ArrayList<>(),
                                        usernameTextField.getText()
                                ))){
                            JOptionPane.showMessageDialog(frame, "User Created Successfully");
                        }
                        else
                            JOptionPane.showMessageDialog(frame, "Unable to create user");
                    }
                    catch (IOException | ParseException ex) {
                        JOptionPane.showMessageDialog(frame, "There was a problem creating the user");
                        throw new RuntimeException(ex);
                    }
                }
                else{
                    JOptionPane.showMessageDialog(frame, "Username should be greater than 5 characters and password cannot be empty");
                }

            }
        });
    }
}
