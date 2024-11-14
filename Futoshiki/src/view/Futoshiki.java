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
    private String currentPress = "";
    private Board board;

    /**
     * Constructor for the Futoshiki JPanel class, this panel will run when the class is being
     * instanced, right now it draws the buttons in the board
     * @param settings The Settings variable is the basic info for the program to run
     */
    public Futoshiki(Settings settings) {
        setBoard(settings);
    }

    /**
     * This is the most important method, it set the board that will be used to play! A lot of
     * math and programming ticks were used because as I refused to created different boards
     * for each size, this method renders the board.
     * @param settings The basic settings the game needs to function
     */
    public void setBoard(Settings settings) {

        // The JPanel gets clean to add the new board
        this.removeAll();

        // The dimension for the board
        int matrix = settings.getSize();

        // This array will have the buttons that we will add to the board, this will later be use to check the state
        gameButtons = new ArrayList<>();

        // This call updates the board that will be used to set the defaults
        defaultBoard(settings);

        // The game panel is the one where we will be playing
        JPanel gamePanel = new JPanel(new GridLayout( 2*matrix-1, 2*matrix-1, 10, 10 ));

        // The side panel is the one where the buttons will be to change the state of the board
        JPanel sidePanel = new JPanel(new GridLayout( matrix+1, 0, 10, 10 ));

        // This panel is a gap between the center of the board and the buttons
        JPanel emptyPanel = new JPanel();
        emptyPanel.setPreferredSize(new Dimension(100, 0));


        int currentPosition = 0; // Current position is the number of items that has been added between buttons and labels
        int cell_i = 0; // The cell_i variable indicates the real row where the buttons are

        // Here we add the buttons that will be the cells for the game
        for (int i = 0; i < 2*matrix-1 ; i++) {

            // Resets the variables
            int cell_j = 0;
            int cons_j = 0;

            for (int j = 0; j < 2*matrix-1 ; j++) {

                // It checks if the label it an empty one where no label is needed
                if(i%2!=0 && j%2!=0){
                    JLabel label = new JLabel();
                    label.setPreferredSize( new Dimension( 300/matrix, 300/matrix ) );
                    gamePanel.add(label);
                    currentPosition++;
                }
                else{

                    // When current position is odd it means that we are adding a label or a constraint
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
                    // If the position is even then it must be a button unless it is an empty variable
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

        // Here we add the side buttons that we will use to change the info
        for (int i = 0; i < matrix; i++) {
            addSideButton(String.valueOf(i+1), matrix, sidePanel);
        }
        addSideButton("Delete", matrix, sidePanel);
        
        add(gamePanel, BorderLayout.WEST);
        add(emptyPanel, BorderLayout.CENTER);
        add(sidePanel, BorderLayout.EAST);
    }

    /**
     * This is the basic button functionality where when pressed will take the last button pressed
     * and assign its value to the currently pressed cell
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        clickedButton.setText(currentPress);
    }

    /**
     * This method will create a cell button, meaning that is the buttons that will go in
     * the matrix board to play
     * @param cell_i The number of i rows of just the buttons, not the labels
     * @param cell_j The number of j rows of just the buttons, not the labels
     * @param matrix This is the matrix size
     * @return
     */
    private JButton getButton(int cell_i, int cell_j, int matrix) {

        //The button that will be added
        JButton cell = new JButton();

        for (Value value : board.getValues()) {

            // It checks if the button being created is in the list of default values
            if( value.getY()== cell_i && value.getX()== cell_j){

                // It assigns to that button the value that corresponds to the board
                cell.setText(String.valueOf(value.getValue()));
                cell.setEnabled( false );
                break;
            }
        }

        // The size of the button gets calculated using this formula to keep it same size
        cell.setPreferredSize( new Dimension( 300/ matrix, 300/ matrix) );

        // Cell color
        cell.setBackground(Color.decode("#ADD8E6"));
        cell.setForeground(Color.black);

        return cell;
    }

    /**
     * It assigns to the board variable the information from the board.json
     * @param settings the settings variable that will be used
     */
    private void defaultBoard(Settings settings){
        try {
            // The board variable receives the Board from the ManageBoard method
            this.board = ManageBoard.getBoard(settings);
        }
        catch (IOException | ParseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Unable to load board", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }
    }

    /**
     * This method creates the buttons necessary for the side panel to be created
     * @param label The label string the button will have
     * @param matrix The matrix dimension
     * @param sidePanel The panel where it will be added
     */
    private void addSideButton(String label, int matrix, JPanel sidePanel){
        Button option = new Button(label);

        // Button size is calculated depending on the matrix size
        option.setPreferredSize( new Dimension( 350/matrix, 350/matrix ) );

        // It adds an action listener to the button that is being created
        option.addActionListener(e -> {
            if(label.equals("Delete"))
                currentPress = "";
            else
                currentPress = label;
        });

        sidePanel.add( option );
    }

}
