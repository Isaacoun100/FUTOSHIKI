package view;

import controller.ManageBoard;
import controller.ManageLeaderboard;
import model.*;
import model.Record;
import org.json.simple.parser.ParseException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

public class Futoshiki extends JPanel implements ActionListener {

    private ArrayList<JButton> gameButtons;
    private String currentPress = "";
    private String previousPress = "";
    private Board board;
    private Integer[][] futoshiki;
    private int matrix;
    private int cellSize;
    private int cellFont;
    private int baseTime = 0;
    private int activeTime = 0;
    private JButton pressedButton;
    private Timer timer;

    /**
     * Constructor for the Futoshiki JPanel class, this panel will run when the class is being
     * instanced, right now it draws the buttons in the board
     * @param settings The Settings variable is the basic info for the program to run
     */
    public Futoshiki(Settings settings) {

        // This call updates the board that will be used to set the defaults
        defaultBoard(settings);
        setBoard(settings);
    }

    /**
     * This is the most important method, it set the board that will be used to play! A lot of
     * math and programming ticks were used because as I refused to created different boards
     * for each size, this method renders the board.
     * @param settings The basic settings the game needs to function
     */
    public void setBoard(Settings settings) {

        if (timer != null && timer.isRunning())
            timer.stop();

        // The JPanel gets clean to add the new board
        this.removeAll();

        // The dimension for the board
        this.matrix = settings.getSize();

        // Cell properties
        this.cellSize = 400/matrix;
        this.cellFont = 100/matrix;

        // Create number array
        futoshiki = new Integer[matrix][matrix];

        // This array will have the buttons that we will add to the board, this will later be use to check the state
        gameButtons = new ArrayList<>();

        // The game panel is the one where we will be playing
        JPanel gamePanel = new JPanel(new GridLayout( 2*matrix-1, 2*matrix-1, 10, 10 ));

        // The side panel is the one where the buttons will be to change the state of the board
        JPanel sidePanel = new JPanel(new GridLayout( matrix+2, 0, 10, 10 ));

        // This panel is a gap between the center of the board and the buttons
        JPanel emptyPanel = new JPanel();
        emptyPanel.setPreferredSize(new Dimension(100, 0));

        // Panel for the timer
        JPanel timePanel = new JPanel();
        sidePanel.add(timePanel);

        int currentPosition = 0; // Current position is the number of items that has been added between buttons and labels
        int cell_i = 0; // The cell_i variable indicates the real row where the buttons are
        ArrayList<Constrain> e = new ArrayList<>( board.getConstraints() );

        // Here we add the buttons that will be the cells for the game
        for (int i = 0; i < 2*matrix-1 ; i++) {

            // Resets the variables
            int cell_j = 0;
            int cons_j = 0;

            for (int j = 0; j < 2*matrix-1 ; j++) {

                // It checks if the label it an empty one where no label is needed
                if(i%2!=0 && j%2!=0){
                    JLabel label = new JLabel();
                    label.setPreferredSize( new Dimension( cellSize, cellSize ) );
                    gamePanel.add(label);
                    currentPosition++;
                }
                else{

                    // When current position is odd it means that we are adding a label or a constraint
                    if(currentPosition%2!=0){
                        JLabel label = new JLabel() ;

                        for (Constrain constraint : e) {
                            if (constraint.getY1() == constraint.getY2() && i % 2 == 0 && constraint.getY1() == cell_i) {
                                if (constraint.getX1() == cell_j - 1) {
                                    label.setText(constraint.getEquals());
                                    e.remove(constraint);
                                    break; // No need to remove the constraint
                                }
                            } else if (constraint.getX1() == constraint.getX2() && constraint.getX1() == cons_j) {
                                if (constraint.getY1() == cell_i - 1) {
                                    if(!constraint.getEquals().equals("<"))
                                        label.setText("V");
                                    else
                                        label.setText("Λ");
                                    e.remove(constraint);
                                    break; // No need to remove the constraint
                                }
                            }
                        }

                        label.setPreferredSize( new Dimension( cellSize, cellSize ) );
                        label.setHorizontalAlignment(SwingConstants.CENTER);
                        label.setFont( new Font( "Arial", Font.BOLD, cellFont ) );
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

        JLabel timerLabel = new JLabel("Time: ");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 24));

        activeTime = 0;
        baseTime = 0;


        switch (settings.getClock()){

            case "Timer":
                // Create a timer to update the label every second
                timer = new Timer(1000, e1 -> {
                    activeTime++;
                    timerLabel.setText("Time: " + formatTime(activeTime));
                });

                timePanel.add(timerLabel);
                timer.start();
                break;

            case "Stopwatch":

                switch (settings.getDifficulty()){
                    case "Easy":
                        activeTime = baseTime = 180*matrix;
                        break;
                    case "Intermediate":
                        activeTime = baseTime = 120*matrix;
                        break;
                    case "Hard":
                        activeTime = baseTime = 90*matrix;
                        break;
                }

                timer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (activeTime > 0) {
                            activeTime--;
                            timerLabel.setText("Time: " + formatTime(activeTime));
                        } else {
                            ((Timer) e.getSource()).stop(); // Stop the timer when it reaches 0
                            JOptionPane.showMessageDialog(null, "Time's up!");
                        }
                    }
                });

                timePanel.add(timerLabel);
                timer.start();

                break;

        }

        if(settings.getSide().equals("Right")){
            add(gamePanel, BorderLayout.WEST);
            add(emptyPanel, BorderLayout.CENTER);
            add(sidePanel, BorderLayout.EAST);
        }
        else if(settings.getSide().equals("Left")){
            add(sidePanel, BorderLayout.WEST);
            add(emptyPanel, BorderLayout.CENTER);
            add(gamePanel, BorderLayout.EAST);
        }

    }

    /**
     * This is the basic button functionality where when pressed will take the last button pressed
     * and assign its value to the currently pressed cell
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        pressedButton = clickedButton;
        previousPress = clickedButton.getText();
        clickedButton.setText(currentPress);
        clickedButton.setFont( new Font( "Arial", Font.BOLD, cellFont ) );
    }

    /**
     * This method saves the current state of the game to the user information
     * @param user The user where the information will be added
     */
    public void saveGame(User user){

        // Gets the user information
        user.setConstrains( board.getConstraints() );
        user.setValues( board.getValues() );

        // Sets up a new matrix
        user.setMatrix( new int[matrix][matrix] );

        int currentPosition = 0;

        // Cycles through the buttons and saves the state
        for (int i = 0; i < matrix; i++) {
            for (int j = 0; j < matrix; j++) {

                String value = gameButtons.get(currentPosition).getText();

                if(!value.isEmpty())
                    user.getMatrix()[i][j] = Integer.parseInt(value);
                else
                    user.getMatrix()[i][j] = 0;

                currentPosition++;
            }
        }
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
        cell.setPreferredSize( new Dimension( cellSize, cellSize) );

        // Cell properties
        cell.setFont( new Font( "Arial", Font.BOLD, cellFont ) );
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
     * It activates the multilevel mode
     * @param user The user that is going to play multilevel
     */
    public void multilevel(User user){

        if(isGameComplete(user)){

            addRecord(user);

            switch (user.getSettings().getDifficulty()){
                case "Easy":
                    JOptionPane.showMessageDialog(null, "Easy level complete! Loading Intermediate");
                    user.getSettings().setDifficulty("Intermediate");
                    defaultBoard(user.getSettings());
                    setBoard(user.getSettings());
                    break;
                case "Intermediate":
                    JOptionPane.showMessageDialog(null, "Intermediate level complete! Loading Hard");
                    JOptionPane.showMessageDialog(null, "Multilevel complete, congrats!");
                    user.getSettings().setDifficulty("Hard");
                    defaultBoard(user.getSettings());
                    setBoard(user.getSettings());
                    break;
                case "Hard":
                    JOptionPane.showMessageDialog(null, "Multilevel complete, congrats!");
                    break;
            }
        }


    }

    /**
     * Checks if the game is complete
     * @param user The user configuration
     * @return It returns true if the game is complete, false if it doesn't meet the requirements
     */
    public boolean isGameComplete(User user ){

        int currentPosition = 0;
        int n = futoshiki.length;

        // Checks if the board has an empty space, also adds the values to a matrix
        for (int i = 0; i < matrix; i++) {
            for (int j = 0; j < matrix; j++) {

                String buttonValue = gameButtons.get(currentPosition).getText();

                if(buttonValue.equals(""))
                    return false;
                else
                    futoshiki[i][j] = Integer.parseInt(buttonValue);

                currentPosition++;
            }
        }

        // Checks if the vertical and horizontal match that they have all the numbers
        for (int i = 0; i < n; i++) {

            // Boolean array
            boolean[] rowCheck = new boolean[n];
            boolean[] colCheck = new boolean[n];

            for (int j = 0; j < n; j++) {
                // Check row
                int rowValue = futoshiki[i][j];
                if (rowValue < 1 || rowValue > n || rowCheck[rowValue - 1]) {
                    return false; // Invalid value or duplicate in row
                }
                rowCheck[rowValue - 1] = true;

                // Check column
                int colValue = futoshiki[j][i];
                if (colValue < 1 || colValue > n || colCheck[colValue - 1]) {
                    return false; // Invalid value or duplicate in column
                }
                colCheck[colValue - 1] = true;
            }
        }

        // Checks if the constraints work
        for( Constrain constraint : board.getConstraints() ){

            if( constraint.getEquals().equals("<") ){
                if( futoshiki[constraint.getY1()][constraint.getX1()] > futoshiki[constraint.getY2()][constraint.getX2()] ){
                    return false;
                }
            }
            else if (constraint.getEquals().equals(">") ){
                if( futoshiki[constraint.getY1()][constraint.getX1()] < futoshiki[constraint.getY2()][constraint.getX2()] ){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if the time record is in the top 10
     * @param user The user to name the record
     */
    public void addRecord(User user){
        if( !user.getUsername().isEmpty() ){
            checkRecord(
                    user.getSettings(),
                    new Record(
                            user.getUsername(),
                            LocalTime.ofSecondOfDay( Math.abs(baseTime - activeTime))
                    ));
        }
    }

    /**
     * Loads the game that the user has saved
     * @param user The user that has the loaded file
     */
    public void loadGame( User user ){

        ArrayList<Value> values = new ArrayList<>();

        board.setValues( user.getValues() );
        board.setConstraints( user.getConstrains() );

        setBoard( user.getSettings() );

        int currentPosition = 0;

        for (int i = 0; i < user.getSettings().getSize(); i++)
            for (int j = 0; j < user.getSettings().getSize(); j++){

                int value = user.getMatrix()[i][j];

                if(value!=0)
                    gameButtons.get(currentPosition).setText( String.valueOf(value) );
                else
                    gameButtons.get(currentPosition).setText("");

                currentPosition++;
            }

    }

    /**
     * Undoes the last move, for some reason, undo and redo have the same logic, and I don't
     * understand why, like I get it because I programmed it, but it doesn't make sense
     */
    public void undo(){

        for(JButton button : gameButtons){
            if(button == pressedButton){
                System.out.println("Equals found");
                String newPress = button.getText();
                button.setText( previousPress );
                previousPress = newPress;
            }
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
        option.setPreferredSize( new Dimension( cellSize, cellSize ) );

        // It adds an action listener to the button that is being created
        option.addActionListener(e -> {
            if(label.equals("Delete"))
                currentPress = "";
            else
                currentPress = label;
        });

        option.setFont(new Font( "Arial", Font.BOLD, cellFont ));
        sidePanel.add( option );
    }

    /**
     * Checks if the new record is correct and the user can be in the top 10
     * @param settings The board settings
     * @param record The new record
     */
    private void checkRecord(Settings settings, Record record){

        if( !settings.getClock().equals("No") ){
            try {
                ManageLeaderboard.addRecord(record, matrix, settings.getDifficulty());
            }
            catch (IOException | ParseException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Unable to load leaderboard", JOptionPane.ERROR_MESSAGE);
                throw new RuntimeException(e);
            }
        }

    }

    /**
     * Formats the seconds in the correct TimeDate to String
     * @param totalSeconds The seconds to parse
     * @return The string of the time converted
     */
    private String formatTime(int totalSeconds) {
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

}
