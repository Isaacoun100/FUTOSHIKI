package controller;

import model.Record;
import model.RecordList;
import model.Leaderboard;

import java.io.PrintWriter;
import java.util.Comparator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

import static java.lang.String.valueOf;

public class ManageLeaderboard {

    static String leaderboardFile = "Futoshiki/src/bin/leaderboard.json";

    /**
     * Using this method we will be able to get the leaderboard
     * @return returns the Leaderboard in the leaderboard.json file
     * @throws IOException The filed doesn't exist
     * @throws ParseException Unable to parse the file
     */
    public static Leaderboard getLeaderboard () throws IOException, ParseException {

        Object object = new JSONParser().parse(new FileReader(leaderboardFile));
        JSONObject jsonObject = (JSONObject) object;

        Leaderboard leaderboard = new Leaderboard();

        for (int i = 3; i <= 10; i++) {

            JSONObject newObject = (JSONObject) jsonObject.get(i+" by "+i);

            RecordList recordList = new RecordList();

            getList( (JSONArray) newObject.get("Easy") , recordList, "Easy" );
            getList( (JSONArray) newObject.get("Intermediate") , recordList, "Intermediate" );
            getList( (JSONArray) newObject.get("Hard") , recordList, "Hard" );

            leaderboard.setRecord(i, recordList);

        }

        return leaderboard;
    }

    /**
     * Takes the record, matrix size and difficulty and adds it to the top 10 if the time fits
     * @param record The record to check
     * @param matrix The size of the board
     * @param difficulty The difficulty of the board
     * @throws IOException The filed doesn't exist
     * @throws ParseException Unable to parse the file
     */
    public static void addRecord(Record record, int matrix, String difficulty) throws IOException, ParseException {

        Object object = new JSONParser().parse(new FileReader(leaderboardFile));
        JSONObject jsonObject = (JSONObject) object;
        JSONObject sizeObject = (JSONObject) jsonObject.get(matrix+" by "+matrix);
        JSONArray difficultyArray = (JSONArray) sizeObject.get(difficulty);

        ArrayList<Record> recordList = new ArrayList<>();
        recordList.add(record);

        for ( Object obj : difficultyArray ) {
            JSONObject difficultyObject = (JSONObject) obj;

            recordList.add(new Record(
                            String.valueOf(difficultyObject.get("username")),
                            LocalTime.parse(String.valueOf(difficultyObject.get("time")))
            ));
        }

        recordList.sort(Comparator.comparing(Record::getTime));
        difficultyArray.clear();

        for( Record newRecord : recordList ) {
            JSONObject difficultyObject = new JSONObject();
            difficultyObject.put("username", newRecord.getUsername());
            difficultyObject.put("time", String.valueOf(newRecord.getTime()));
            difficultyArray.add(difficultyObject);
        }

        difficultyArray.remove(difficultyArray.size()-1);

        PrintWriter writer = new PrintWriter(leaderboardFile);
        writer.println(jsonObject.toJSONString());

        writer.flush();
        writer.close();
    }

    /**
     * Returns Auxiliar class to addRecord
     * @param newArray The array to add
     * @param recordList The list of records
     * @param difficulty The difficulty
     */
    private static void getList ( JSONArray newArray, RecordList recordList, String difficulty ){
        for (Object record : newArray) {
            JSONObject recordObject = (JSONObject) record;

            recordList.setRecord(new Record(
                    valueOf(recordObject.get("username")),
                    LocalTime.parse(valueOf(recordObject.get("time")))),
                    difficulty
            );

        }
    }

}
