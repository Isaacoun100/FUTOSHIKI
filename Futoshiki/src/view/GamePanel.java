package view;

import model.Settings;
import model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel {


    private JPanel gamePanel;
    private JMenuItem saveGameMenu;
    private JMenuItem loadGameMenu;
    private JMenuItem finishGameMenu;
    private JMenuItem resetGameMenu;
    private JMenuItem undoMenu;
    private JMenuItem redoMenu;
    private JMenuItem mainMenu;
    private JMenuItem settingsMenu;
    private JMenuItem topMenu;
    private JMenuItem aboutUsMenu;
    private JMenuItem helpMenu;
    private JMenuItem exitMenuItem;
    private JPanel boardPanel;
    private Futoshiki gameFutoshiki;
    private User user;

    public GamePanel(User user ) {

        this.user = user;

        JFrame frame = new JFrame("Futoshiki");
        frame.setContentPane(gamePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(850+(user.getSettings().getSize()*55), 700+(user.getSettings().getSize()*35) );
        frame.setResizable(false);
        frame.setVisible(true);

        exitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                System.exit(0);
            }
        });

        helpMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                frame.dispose();
                new HelpScreen(user);
            }
        });

        aboutUsMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                frame.dispose();
                new AboutUs(user);
            }
        });

        topMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                frame.dispose();
                new TopScreen(user);
            }
        });

        settingsMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                frame.dispose();
                new SettingsScreen(user);
            }
        });

        mainMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                frame.dispose();
                new HomeScreen(user);
            }
        });

        resetGameMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                gameFutoshiki.setBoard(user.getSettings());
            }
        });

        finishGameMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                if( gameFutoshiki.isGameComplete(user)){
                    JOptionPane.showMessageDialog(frame, "Congrats, you have finished the game!");
                }
                else {
                    JOptionPane.showMessageDialog(frame, "Sorry, you have not finished the game!");
                }
            }
        });

    }

    private void createUIComponents(){
        gameFutoshiki = new Futoshiki(user.getSettings());
    }

}
