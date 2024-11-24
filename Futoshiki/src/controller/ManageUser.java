package controller;

import model.Constrain;
import model.Settings;
import model.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ManageUser {

    // User file that will look for
    static String userFile = "Futoshiki/src/bin/user.json";

    public static User signIn ( String username, String password ) throws IOException, ParseException {

        Object file = new JSONParser().parse(new FileReader(userFile));
        JSONArray jsonArray = (JSONArray) file;

        for( Object object : jsonArray ) {
            JSONObject jsonObject = (JSONObject) object;
            JSONObject settings = (JSONObject) jsonObject.get("settings");

            if( jsonObject.get("username").equals(username) && jsonObject.get("password").equals(password) ) {

                JSONArray currentGame = (JSONArray) jsonObject.get("matrix");
                JSONArray currentConstrain = (JSONArray) jsonObject.get("constraints");

                int[][] matrix = new int[currentGame.size()][currentGame.size()];
                ArrayList<Constrain> constrains = new ArrayList<>();

                for(int i = 0; i < currentGame.size(); i++) {
                    for(int j = 0; j < currentGame.size(); j++) {
                        JSONArray currentConstraint = (JSONArray) currentGame.get(i);
                        matrix[i][j] = Integer.parseInt(( String.valueOf(currentConstraint.get(j)) ));
                    }
                }

                ManageBoard.createConstrain(currentConstrain, constrains);

                return new User(
                        new Settings(
                                Integer.parseInt( String.valueOf( settings.get("size") ) ),
                                String.valueOf( settings.get("difficulty") ),
                                Boolean.valueOf( String.valueOf(settings.get("multilevel") ) ),
                                String.valueOf( settings.get("clock") ),
                                String.valueOf( settings.get("button") )
                        ),
                        password,
                        matrix,
                        constrains,
                        username
                );

            }

        }

        return null;

    }

}
