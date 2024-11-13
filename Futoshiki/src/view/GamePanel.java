package view;

import model.Settings;

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
    private Settings settings;

    public GamePanel(Settings settings ) {

        this.settings = settings;

        JFrame frame = new JFrame("Futoshiki");
        frame.setContentPane(gamePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650+(settings.getSize()*55), 600+(settings.getSize()*35) );
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
                new HelpScreen();
            }
        });

        aboutUsMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                frame.dispose();
                new AboutUs();
            }
        });

        topMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                frame.dispose();
                new TopScreen();
            }
        });

        settingsMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                frame.dispose();
                new SettingsScreen(settings);
            }
        });

        mainMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                frame.dispose();
                new HomeScreen();
            }
        });

    }

    private void createUIComponents(){
        gameFutoshiki = new Futoshiki(settings);
    }

}
