package controller;

import model.Board;
import model.Constrain;
import model.Settings;
import model.Value;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class ManageBoard {

    static String boardFile = "Futoshiki/src/bin/boards.json";

    public static Board getBoard(Settings settings) throws IOException, ParseException {

        Random random = new Random();

        Object object = new JSONParser().parse(new FileReader(boardFile));
        JSONObject jsonObject = (JSONObject) object;

        JSONObject difficulty = (JSONObject) jsonObject.get(settings.getDifficulty());
        JSONArray size = (JSONArray) difficulty.get(String.valueOf(settings.getSize()));
        JSONObject board = (JSONObject) size.get(random.nextInt(size.size()));

        JSONArray values = (JSONArray) board.get("values");
        JSONArray constraints = (JSONArray) board.get("constraints");

        ArrayList<Value> valueList = new ArrayList<>();
        ArrayList<Constrain> constraintList = new ArrayList<>();

        createConstrain(constraints, constraintList, values, valueList);

        return new Board(valueList, constraintList);
    }

    /**
     * Adds a new constraint to the list
     * @param currentConstrain The constraint to add
     * @param constrains The array of constraint
     * @param values The value that has.
     * @param valueList The list of values
     */
    static void createConstrain(JSONArray currentConstrain, ArrayList<Constrain> constrains, JSONArray values, ArrayList<Value> valueList) {

        for( Object newObject : currentConstrain ) {
            JSONObject newConstrain = (JSONObject) newObject;

            constrains.add(new Constrain(
                    Integer.parseInt(String.valueOf(newConstrain.get("x1"))),
                    Integer.parseInt(String.valueOf(newConstrain.get("y1"))),
                    Integer.parseInt(String.valueOf(newConstrain.get("x2"))),
                    Integer.parseInt(String.valueOf(newConstrain.get("y2"))),
                    String.valueOf(newConstrain.get("equals"))
            ));

        }

        for ( Object newObj : values){
            JSONObject value = (JSONObject) newObj;
            valueList.add( new Value(
                    Integer.parseInt(String.valueOf(value.get("x"))),
                    Integer.parseInt(String.valueOf(value.get("y"))),
                    Integer.parseInt(String.valueOf(value.get("value"))))
            );
        }
    }

}
