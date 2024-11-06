package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TopScreen {
    private JPanel topPanel;
    private JButton returnToPreviousMenuButton;
    private JComboBox comboBox1;
    private JList list1;
    private JList list2;
    private JList list3;

    public TopScreen() {

        JFrame frame = new JFrame("Top 10");
        frame.setContentPane(topPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setResizable(false);
        frame.setVisible(true);

        returnToPreviousMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new HomeScreen();
            }
        });
    }
}
