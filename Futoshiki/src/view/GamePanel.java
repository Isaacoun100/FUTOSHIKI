package view;

import controller.ManageUser;
import model.Settings;
import model.User;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

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
        frame.setSize(850+(user.getSettings().getSize()*55), 750+(user.getSettings().getSize()*35) );
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

                if(user.getSettings().isMultilevel())

                    gameFutoshiki.multilevel(user);

                else {

                    if (gameFutoshiki.isGameComplete(user)){
                        gameFutoshiki.addRecord(user);
                        JOptionPane.showMessageDialog(frame, "Congrats, you have finished the game!");
                    }
                    else
                        JOptionPane.showMessageDialog(frame, "Sorry, you have not finished the game!");
                }
            }
        });

        saveGameMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                gameFutoshiki.saveGame(user);
                try {
                    ManageUser.setUser(user);
                }
                catch (IOException | ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });

         loadGameMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                gameFutoshiki.loadGame(user);
            }
        });

        undoMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                gameFutoshiki.undo();
            }
        });

        redoMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                gameFutoshiki.undo();
            }
        });

    }

    /**
     * Loads the board where we will be playing
     */
    private void createUIComponents(){
        gameFutoshiki = new Futoshiki(user.getSettings());
    }

}
