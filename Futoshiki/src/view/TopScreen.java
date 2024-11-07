package view;

import controller.ManageLeaderboard;
import model.Leaderboard;
import model.Record;
import model.RecordList;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

public class TopScreen {
    private JPanel topPanel;
    private JButton returnToPreviousMenuButton;
    private JComboBox sizeComboBox;
    private JList hardList;
    private JList intermediateList;
    private JList easyList;
    Leaderboard leaderboard = addValues();

    public TopScreen() {

        JFrame frame = new JFrame("Top 10");
        frame.setContentPane(topPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setResizable(false);
        frame.setVisible(true);

        System.out.println("Pin point");

        returnToPreviousMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new HomeScreen();
            }
        });
        sizeComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedItem = (String) e.getItem();

                    // Perform different actions based on the selected item
                    switch (selectedItem) {
                        case "3 by 3":
                            addValues( leaderboard.getRecord(3) );
                            break;
                        case "4 by 4":
                            addValues( leaderboard.getRecord(4) );
                            break;
                        case "5 by 5":
                            addValues( leaderboard.getRecord(5) );
                            break;
                        case "6 by 6":
                            addValues( leaderboard.getRecord(6) );
                            break;
                        case "7 by 7":
                            addValues( leaderboard.getRecord(7) );
                            break;
                        case "8 by 8":
                            addValues( leaderboard.getRecord(8) );
                            break;
                        case "9 by 9":
                            addValues( leaderboard.getRecord(9) );
                            break;
                        case "10 by 10":
                            addValues( leaderboard.getRecord(10) );
                            break;
                        default:
                            break;
                    }
                }
            }
        });
    }

    private Leaderboard addValues() {

        try {
            Leaderboard leaderboard = ManageLeaderboard.getLeaderboard();
            addValues(leaderboard.getRecord(3));
            return leaderboard;
        }
        catch (IOException | ParseException e) {
            System.out.println("Unable to load the file");
            throw new RuntimeException(e);
        }
    }

    public void addValues(RecordList recordList){

        DefaultListModel<String> easyList = new DefaultListModel<>();
        DefaultListModel<String> intermediateList = new DefaultListModel<>();
        DefaultListModel<String> hardList = new DefaultListModel<>();

        for (Record record : recordList.getEasy()){
            easyList.addElement( record.getUsername() + "  →  " + record.getTime().toString() );
        }

        for (Record record : recordList.getIntermediate()){
            intermediateList.addElement( record.getUsername() + "  →  " + record.getTime().toString() );
        }

        for (Record record : recordList.getHard()){
            hardList.addElement( record.getUsername() + "  →  " + record.getTime().toString() );
        }

        this.hardList.setModel(easyList);
        this.intermediateList.setModel(intermediateList);
        this.easyList.setModel(hardList);

    }

}
