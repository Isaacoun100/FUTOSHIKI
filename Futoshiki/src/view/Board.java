package view;

import javax.swing.*;
import java.awt.*;

public class Board {

    private JPanel BoardPanel;

    public Board(){

        JFrame frame = new JFrame("Futoshiki");
        frame.setContentPane(BoardPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);

        // Create the menu bar
        JMenuBar menuBar = new JMenuBar();

        // Create a menu and add it to the menu bar
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        // Create and add menu items to the menu
        JMenuItem saveGame = new JMenuItem("Save game");
        JMenuItem loadGame = new JMenuItem("Load game");
        JMenuItem finishGame = new JMenuItem("Finish game");
        JMenuItem resetGame = new JMenuItem("Reset game");

        // Add the items to the file menu
        fileMenu.add(saveGame);
        fileMenu.add(loadGame);
        fileMenu.addSeparator();
        fileMenu.add(finishGame);
        fileMenu.add(resetGame);

        JMenu optionsMenu = new JMenu("Option");
        menuBar.add(optionsMenu);

        // Create and add menu items to the menu
        JMenuItem undo = new JMenuItem("Undo (Ctrl+Z)");
        JMenuItem redo = new JMenuItem("Redo (Ctrl+Y)");

        // Add the items to the file menu
        optionsMenu.add(undo);
        optionsMenu.add(redo);

        JMenu moreMenu = new JMenu("More");
        menuBar.add(moreMenu);

        // Create and add menu items to the menu
        JMenuItem mainMenu = new JMenuItem("Main menu");
        JMenuItem settings = new JMenuItem("Settings");
        JMenuItem top10 = new JMenuItem("Top 10");
        JMenuItem aboutUs = new JMenuItem("About us");
        JMenuItem help = new JMenuItem("Help");

        // Add the items to the file menu
        optionsMenu.add(undo);
        optionsMenu.add(redo);

        // Use a JPanel with BorderLayout and add the menu bar to the SOUTH
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(menuBar, BorderLayout.NORTH);

        // Add the panel to the frame
        frame.add(panel);

        // Make the frame visible and non-resizable
        frame.setResizable(false);
        frame.setVisible(true);

    }

}
