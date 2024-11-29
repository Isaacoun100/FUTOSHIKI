package controller;

import model.Constrain;
import model.Settings;
import model.User;
import model.Value;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ManageUser {

    // User file that will look for
    static String userFile = "Futoshiki/src/bin/user.json";

    /**
     * This method will check if the user exists
     * @param username The username to check
     * @param password The password to check
     * @return It returns a User if the method finds one, NULL if it doesn't
     * @throws IOException If the file doesn't exist
     * @throws ParseException if the file can't be parsed
     */
    public static User signIn ( String username, String password ) throws IOException, ParseException {

        Object file = new JSONParser().parse(new FileReader(userFile));
        JSONArray jsonArray = (JSONArray) file;

        for( Object object : jsonArray ) {
            JSONObject jsonObject = (JSONObject) object;
            JSONObject settings = (JSONObject) jsonObject.get("settings");

            if( jsonObject.get("username").equals(username) && jsonObject.get("password").equals(password) ) {

                JSONArray currentGame = (JSONArray) jsonObject.get("matrix");
                JSONArray currentConstrain = (JSONArray) jsonObject.get("constraints");

                JSONArray values = (JSONArray) jsonObject.get("values");
                ArrayList<Value> valueList = new ArrayList<>();

                int[][] matrix = new int[currentGame.size()][currentGame.size()];
                ArrayList<Constrain> constrains = new ArrayList<>();

                for(int i = 0; i < currentGame.size(); i++) {
                    for(int j = 0; j < currentGame.size(); j++) {
                        JSONArray currentConstraint = (JSONArray) currentGame.get(i);
                        matrix[i][j] = Integer.parseInt(( String.valueOf(currentConstraint.get(j)) ));
                    }
                }

                ManageBoard.createConstrain(currentConstrain, constrains, values, valueList);

                return new User(
                        new Settings(
                                Integer.parseInt( String.valueOf( settings.get("size") ) ),
                                String.valueOf( settings.get("difficulty") ),
                                Boolean.parseBoolean( String.valueOf(settings.get("multilevel") ) ),
                                String.valueOf( settings.get("clock") ),
                                String.valueOf( settings.get("button") )
                        ),
                        password,
                        matrix,
                        constrains,
                        valueList,
                        username
                );

            }

        }

        return null;

    }

    /**
     * It creates and saves a new user based on the User provided
     * @param user The user that will be used to create
     * @return True if the user was created, false if not
     * @throws IOException If the file doesn't exist
     * @throws ParseException if the file can't be parsed
     */
    public static boolean createUser ( User user ) throws IOException, ParseException {
        Object file = new JSONParser().parse(new FileReader(userFile));
        JSONArray jsonArray = (JSONArray) file;

        if(!isUser(user.getUsername())) {

            JSONObject newUser = new JSONObject();
            JSONObject userSettings = new JSONObject();

            newUser.put("username", user.getUsername());
            newUser.put("password", user.getPassword());
            newUser.put("matrix", new JSONArray());
            newUser.put("constraints", new JSONArray());
            newUser.put("values", new JSONArray());

            userSettings.put("size", user.getSettings().getSize());
            userSettings.put("difficulty", user.getSettings().getDifficulty());
            userSettings.put("multilevel", user.getSettings().isMultilevel());
            userSettings.put("clock", user.getSettings().getClock());
            userSettings.put("button", user.getSettings().getSide());

            newUser.put("settings", userSettings);
            jsonArray.add(newUser);


            PrintWriter writer = new PrintWriter(userFile);
            writer.println(jsonArray.toJSONString());

            writer.flush();
            writer.close();

            return true;
        }

        return false;
    }

    /**
     * This method takes the User and finds the user it matches in the file and it updates it
     * @param user The user to update and its information
     * @throws IOException If the file doesn't exist
     * @throws ParseException if the file can't be parsed
     */
    public static void setUser(User user) throws IOException, ParseException {
        Object file = new JSONParser().parse(new FileReader(userFile));
        JSONArray jsonArray = (JSONArray) file;

        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject) object;

            // Check if the username matches
            if (jsonObject.get("username").equals(user.getUsername())) {
                // Update settings
                JSONObject settings = (JSONObject) jsonObject.get("settings");
                settings.put("size", user.getSettings().getSize());
                settings.put("difficulty", user.getSettings().getDifficulty());
                settings.put("multilevel", user.getSettings().isMultilevel());
                settings.put("clock", user.getSettings().getClock());
                settings.put("button", user.getSettings().getSide());
                jsonObject.put("settings", settings);

                // Update matrix
                JSONArray matrixArray = new JSONArray();
                for (int[] row : user.getMatrix()) {
                    JSONArray rowArray = new JSONArray();
                    for (int cell : row) {
                        rowArray.add(cell);
                    }
                    matrixArray.add(rowArray);
                }
                jsonObject.put("matrix", matrixArray);

                // Update constraints
                JSONArray constraintsArray = new JSONArray();
                for (Constrain constrain : user.getConstrains()) {
                    JSONObject constrainObject = new JSONObject();
                    constrainObject.put("x1", constrain.getX1());
                    constrainObject.put("y1", constrain.getY1());
                    constrainObject.put("x2", constrain.getX2());
                    constrainObject.put("y2", constrain.getY2());
                    constrainObject.put("equals", constrain.getEquals());
                    constraintsArray.add(constrainObject);
                }
                jsonObject.put("constraints", constraintsArray);

                // Update values
                JSONArray valuesArray = new JSONArray();
                for (Value value : user.getValues()) {
                    JSONObject valueObject = new JSONObject();
                    valueObject.put("x", value.getX());
                    valueObject.put("y", value.getY());
                    valueObject.put("value", value.getValue());
                    valuesArray.add(valueObject);
                }
                jsonObject.put("values", valuesArray);

                break; // Stop iterating as the user is found
            }
        }

        // Write back to the file
        PrintWriter writer = new PrintWriter(userFile);
        writer.println(jsonArray.toJSONString());
        writer.flush();
        writer.close();
    }

    /**
     * Checks if the username provided exists
     * @param username The username to check
     * @return
     * @throws IOException If the file doesn't exist
     * @throws ParseException if the file can't be parsed
     */
    private static boolean isUser(String username) throws IOException, ParseException {

        Object file = new JSONParser().parse(new FileReader(userFile));
        JSONArray jsonArray = (JSONArray) file;

        for( Object object : jsonArray ) {
            JSONObject jsonObject = (JSONObject) object;

            if(jsonObject.get("username").equals(username))
                return true;

        }

        return false;
    }

}
