package view;

import controller.ManageBoard;
import model.Board;
import model.Constrain;
import model.Settings;
import model.Value;
import org.json.simple.parser.ParseException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class Futoshiki extends JPanel implements ActionListener {

    private ArrayList<JButton> gameButtons;
    private ArrayList<JLabel> contrainsLabel;
    private ArrayList<Button> sideButtons;
    private String currentPress = "";
    private Board board;

    public Futoshiki(Settings settings) {
        setBoard(settings);
    }

    public void setBoard(Settings settings) {

        this.removeAll();

        int matrix = settings.getSize();

        gameButtons = new ArrayList<>();
        sideButtons = new ArrayList<>();

        // This call updates the board that will be used to set the defaults
        defaultBoard(settings);

        JPanel gamePanel = new JPanel(new GridLayout( 2*matrix-1, 2*matrix-1, 10, 10 ));
        JPanel sidePanel = new JPanel(new GridLayout( matrix+1, 0, 10, 10 ));
        JPanel panel = new JPanel(new BorderLayout());

        JPanel emptyPanel = new JPanel();
        emptyPanel.setPreferredSize(new Dimension(100, 0));

        int currentPosition = 0;
        int cell_i = 0;

        for (int i = 0; i < 2*matrix-1 ; i++) {

            int cell_j = 0;
            int cons_j = 0;

            for (int j = 0; j < 2*matrix-1 ; j++) {

                if(i%2!=0 && j%2!=0){
                    JLabel label = new JLabel();
                    label.setPreferredSize( new Dimension( 300/matrix, 300/matrix ) );
                    gamePanel.add(label);
                    currentPosition++;
                }
                else{
                    if(currentPosition%2!=0){
                        JLabel label = new JLabel() ;

                        for (Constrain constraint : board.getConstraints()) {

                            if( constraint.getY1() == constraint.getY2() && i%2==0 && constraint.getY1() == cell_i ){
                                if( constraint.getX1() == cell_j-1){
                                    label.setText(constraint.getEquals());
                                    board.getConstraints().remove(constraint);
                                    break;
                                }
                            }
                            else if (constraint.getX1() == constraint.getX2() && constraint.getX1() == cons_j ) {
                                if( constraint.getY1() == cell_i-1){
                                    label.setText(constraint.getEquals());
                                    board.getConstraints().remove(constraint);
                                    break;
                                }
                            }

                        }

                        label.setPreferredSize( new Dimension( 300/matrix, 300/matrix ) );
                        label.setHorizontalAlignment(SwingConstants.CENTER);
                        gamePanel.add( label );
                        currentPosition++;
                        cons_j++;
                    }
                    else{
                        JButton cell = getButton(cell_i, cell_j, matrix);
                        cell.addActionListener(this);
                        gamePanel.add( cell );
                        gameButtons.add( cell );
                        currentPosition++;
                        cell_j++;
                    }
                }
            }

            if(i%2==0)
                cell_i++;
        }

        for (int i = 0; i < matrix; i++) {
            addCell(String.valueOf(i+1), matrix, sidePanel);
        }

        addCell("Delete", matrix, sidePanel);

        panel.add(gamePanel, BorderLayout.WEST);
        panel.add(emptyPanel, BorderLayout.CENTER);
        panel.add(sidePanel, BorderLayout.EAST);
        add(panel);
    }

    private JButton getButton(int cell_i, int cell_j, int matrix) {
        JButton cell = new JButton();

        for (Value value : board.getValues()) {
            if( value.getY()== cell_i && value.getX()== cell_j){
                cell.setText(String.valueOf(value.getValue()));
                cell.setEnabled( false );
                cell.addActionListener(e -> cell.setText(currentPress));
                break;
            }
        }

        cell.setPreferredSize( new Dimension( 300/ matrix, 300/ matrix) );
        cell.setBackground(Color.decode("#ADD8E6"));
        cell.setForeground(Color.black);
        return cell;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        clickedButton.setText(currentPress);
    }

    private void defaultBoard(Settings settings){
        try {
            this.board = ManageBoard.getBoard(settings);
        }
        catch (IOException | ParseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Unable to load board", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }
    }

    private void addCell (String label, int matrix, JPanel sidePanel){
        Button option = new Button(label);
        option.setPreferredSize( new Dimension( 350/matrix, 350/matrix ) );

        option.addActionListener(e -> {
            if(label.equals("Delete"))
                currentPress = "";
            else
                currentPress = label;
        });

        sidePanel.add( option );
        sideButtons.add( option );
    }

}
