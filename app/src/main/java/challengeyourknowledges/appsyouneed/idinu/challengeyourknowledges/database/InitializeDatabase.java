package challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.database;


import android.content.Context;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.R;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.model.AppInfo;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.model.Game;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.model.PlayerState;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.model.Question;

public class InitializeDatabase {
    private static final String FILEPATH = "input.txt";

    public static void initializeDatabase(DatabaseHandler databaseHandler, Context context) {

        File inputQuestions = new File(FILEPATH);
        final Resources resources = context.getResources();
        InputStream inputStream = resources.openRawResource(R.raw.input);
        AppInfo appInfo = new AppInfo(0L, System.currentTimeMillis());
        PlayerState playerState = new PlayerState(0,0,  "player1");
        if (databaseHandler.getAppInfo() == null) {
            databaseHandler.addAppInfo(appInfo);
        }
        if (databaseHandler.getAllPlayerState().size() == 0) {
            databaseHandler.addPlayerState(playerState);
        }
        if (databaseHandler.getAllQuestions().size() == 0) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split("/");
                    Question question = makeQuestion(parts);
                    databaseHandler.addQuestion(question);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Game game = new Game(0, 7, playerState.getId());
        if (databaseHandler.getAllGames().size() == 0) {
            databaseHandler.addGame(game);
        }
    }

    public static Question makeQuestion(String[] parts) {
        String text = "";
        String type = "";
        String answear1 = "";
        String answear2 = "";
        String answear3 = "";
        String correct_answear = "";
        String domain = "";
        domain = checkDomain(parts[0]);
        if (parts[0].contains("fast")){
            type = "fast";
        }
        if( parts[0].contains("normal")){
            type = "normal";
        }
        if (parts[0].contains("af")) {
            type = "af";
            text = parts[1];
            correct_answear = parts[2];
            answear1 = "";
            answear2 = "";
            answear3 = "";
        } else {
            text = parts[1];
            correct_answear = parts[2];
            answear1 = parts[3];
            answear2 = parts[4];
            System.out.println(text);
            answear3 = parts[5];
        }
        Question question = new Question(0, text, type, answear1, answear2,
                answear3, correct_answear, domain, 0);
        return question;
    }

    public static String checkDomain(String type) {
        if (type.contains("limbaromana")){
            return "limbaromana";
        }
        if (type.contains("istorie")) {
            return "istorie";
        }
        return "";
    }
}
