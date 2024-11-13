package view;

import model.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Futoshiki extends JPanel implements ActionListener {

    private ArrayList<Button> gameButtons;
    private ArrayList<JLabel> contrainsLabel;
    private ArrayList<Button> sideButtons;

    public Futoshiki(Settings settings) {

        int matrix = settings.getSize();

        gameButtons = new ArrayList<>();
        sideButtons = new ArrayList<>();

        JPanel gamePanel = new JPanel(new GridLayout( 2*matrix-1, 2*matrix-1, 10, 10 ));
        JPanel sidePanel = new JPanel(new GridLayout( matrix, 0, 10, 10 ));
        JPanel panel = new JPanel(new BorderLayout());

        JPanel emptyPanel = new JPanel();
        emptyPanel.setPreferredSize(new Dimension(100, 0));

        int currentPosition = 0;

        for (int i = 0; i < 2*matrix-1 ; i++) {

            for (int j = 0; j < 2*matrix-1 ; j++) {

                if(i%2!=0 && j%2!=0){
                    JLabel label = new JLabel();
                    label.setPreferredSize( new Dimension( 300/matrix, 300/matrix ) );
                    gamePanel.add(label);
                    currentPosition++;
                }
                else{
                    if(currentPosition%2!=0){
                        JLabel label = new JLabel(String.valueOf(currentPosition));
                        label.setPreferredSize( new Dimension( 300/matrix, 300/matrix ) );
                        label.setHorizontalAlignment(SwingConstants.CENTER);
                        gamePanel.add( label );
                        currentPosition++;
                    }
                    else{
                        Button cell = new Button(String.valueOf(currentPosition));
                        cell.setPreferredSize( new Dimension( 300/matrix, 300/matrix ) );
                        cell.addActionListener(this);
                        gamePanel.add( cell );
                        gameButtons.add( cell );
                        currentPosition++;
                    }
                }
            }
        }

        for (int i = 0; i < matrix; i++) {
            Button option = new Button( String.valueOf( i+1 ) );
            option.setPreferredSize( new Dimension( 350/matrix, 350/matrix ) );
            sidePanel.add( option );
            sideButtons.add( option );
        }

        panel.add(gamePanel, BorderLayout.WEST);
        panel.add(emptyPanel, BorderLayout.CENTER);
        panel.add(sidePanel, BorderLayout.EAST);
        add(panel);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Button clickedButton = (Button) e.getSource();
        System.out.println("Button " + clickedButton.getLabel() + " clicked");
    }

    public ArrayList<Button> getGameButtons() {
        return gameButtons;
    }

    public ArrayList<Button> getSideButtons() {
        return sideButtons;
    }

    public ArrayList<JLabel> getContrainsLabel() {
        return contrainsLabel;
    }

}
