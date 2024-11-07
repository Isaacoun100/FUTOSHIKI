package controller;

import model.Settings;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import static java.lang.String.valueOf;

public class ManageSettings {

    // Settings file that will look for
    static String settingsFile = "Futoshiki/src/bin/settings.json";

    /**
     * It returns the Settings information
     * @return It returns a Settings variable
     * @throws IOException Cannot find the file
     * @throws ParseException Unable to parse the file
     */
    public static Settings getSettings() throws IOException, ParseException {

        Object object = new JSONParser().parse(new FileReader(settingsFile));
        JSONObject jsonObject = (JSONObject) object;

        return new Settings(
                Integer.parseInt(valueOf(jsonObject.get("size"))),
                valueOf(jsonObject.get("difficulty")),
                Boolean.parseBoolean(valueOf(jsonObject.get("multilevel"))),
                valueOf(jsonObject.get("clock")),
                valueOf(jsonObject.get("button"))
        );
    }

    /**
     * It receives a Settings value, and it assigns its values to the settings.json file
     * @param settings The Settings value to write to the file
     * @throws IOException Cannot find the file
     * @throws ParseException Unable to parse the file
     */
    public static void setSettings(Settings settings) throws IOException, ParseException {

        Object object = new JSONParser().parse(new FileReader(settingsFile));
        JSONObject jsonObject = (JSONObject) object;

        jsonObject.put("size", settings.getSize());
        jsonObject.put("difficulty", settings.getDifficulty());
        jsonObject.put("multilevel", settings.isMultilevel());
        jsonObject.put("clock", settings.getClock());
        jsonObject.put("button", settings.getSide());

        PrintWriter writer = new PrintWriter(settingsFile);
        writer.println(jsonObject.toJSONString());

        writer.flush();
        writer.close();

    }

}
