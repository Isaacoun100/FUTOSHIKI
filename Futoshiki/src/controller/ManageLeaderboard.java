package controller;

import model.Leaderboard;
import model.Record;
import model.RecordList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalTime;

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

            JSONArray easyArray = (JSONArray) newObject.get("Easy");
            JSONArray intermediateArray = (JSONArray) newObject.get("Intermediate");
            JSONArray hardArray = (JSONArray) newObject.get("Hard");

            RecordList recordList = new RecordList();

            for (Object record : easyArray) {
                JSONObject recordObject = (JSONObject) record;

                recordList.addEasy(new Record(
                        valueOf(recordObject.get("username")),
                        LocalTime.parse(valueOf(recordObject.get("time")))
                ));
            }

            for (Object record : intermediateArray) {
                JSONObject recordObject = (JSONObject) record;

                recordList.addIntermediate(new Record(
                        valueOf(recordObject.get("username")),
                        LocalTime.parse(valueOf(recordObject.get("time")))
                ));
            }

            for (Object record : hardArray) {
                JSONObject recordObject = (JSONObject) record;

                recordList.addHard(new Record(
                        valueOf(recordObject.get("username")),
                        LocalTime.parse(valueOf(recordObject.get("time")))
                ));
            }

            leaderboard.setRecord(i, recordList);

        }

        return leaderboard;
    }

    public static void setLeaderboard (Leaderboard leaderboard) throws IOException, ParseException {

//        Object object = new JSONParser().parse(new FileReader(leaderboardFile));
//        JSONObject jsonObject = (JSONObject) object;
//
//        JSONArray easyArray = new JSONArray();
//        JSONArray intermediateArray = new JSONArray();
//        JSONArray hardArray = new JSONArray();
//
//        for (Record record : leaderboard.getEasy()) {
//            JSONObject newObject = new JSONObject();
//            newObject.put("username", record.getUsername());
//            newObject.put("time", valueOf(record.getTime()));
//            easyArray.add(newObject);
//        }
//
//        for (Record record : leaderboard.getIntermediate()) {
//            JSONObject newObject = new JSONObject();
//            newObject.put("username", record.getUsername());
//            newObject.put("time", valueOf(record.getTime()));
//            intermediateArray.add(newObject);
//        }
//
//        for (Record record : leaderboard.getHard()) {
//            JSONObject newObject = new JSONObject();
//            newObject.put("username", record.getUsername());
//            newObject.put("time", valueOf(record.getTime()));
//            hardArray.add(newObject);
//        }
//
//        jsonObject.put("Easy", easyArray);
//        jsonObject.put("Intermediate", intermediateArray);
//        jsonObject.put("Hard", hardArray);
//
//        PrintWriter pw = new PrintWriter(leaderboardFile);
//        pw.write(jsonObject.toJSONString());
//
//        pw.flush();
//        pw.close();

    }

}
