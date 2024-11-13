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

        for ( Object newObj : values){
            JSONObject value = (JSONObject) newObj;
            valueList.add( new Value(
                    Integer.parseInt(String.valueOf(value.get("x"))),
                    Integer.parseInt(String.valueOf(value.get("y"))),
                    Integer.parseInt(String.valueOf(value.get("value"))))
            );
        }

        for ( Object newObj : constraints){
            JSONObject constraint = (JSONObject) newObj;
            constraintList.add( new Constrain(
                    Integer.parseInt(String.valueOf(constraint.get("x1"))),
                    Integer.parseInt(String.valueOf(constraint.get("y1"))),
                    Integer.parseInt(String.valueOf(constraint.get("x2"))),
                    Integer.parseInt(String.valueOf(constraint.get("y2"))),
                    String.valueOf(constraint.get("equals"))
            ));
        }

        return new Board(valueList, constraintList);
    }

}
