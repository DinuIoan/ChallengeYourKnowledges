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

    private static final String GENERAL_INFO = "GENERAL_INFO";
    private static final int GENERAL_INFO_POINTS = 12;

    private static final String SEASON1 = "SEASON1";
    private static final int SEASON1_POINTS = 20;

    private static final String SEASON2 = "SEASON2";
    private static final int SEASON2_POINTS = 18;

    private static final String SEASON3 = "SEASON3";
    private static final int SEASON3_POINTS = 16;

    private static final String SEASON4 = "SEASON4";
    private static final int SEASON4_POINTS = 14;

    private static final String SEASON5 = "SEASON5";
    private static final int SEASON5_POINTS = 12;

    private static final String SEASON6 = "SEASON6";
    private static final int SEASON6_POINTS = 10;

    private static final String SEASON7 = "SEASON7";
    private static final int SEASON7_POINTS = 8;

    private static final String WHO_SAID = "WHO_SAID";
    private static final int WHO_SAID_POINTS = 12;

    private static final String GLOBAL = "GLOBAL";
    private static final int GLOBAL_POINTS = 12;

    public static void initializeDatabase(DatabaseHandler databaseHandler, Context context) {

        File inputQuestions = new File(FILEPATH);
        final Resources resources = context.getResources();
        InputStream inputStream = resources.openRawResource(R.raw.input);
        AppInfo appInfo = new AppInfo(0L, System.currentTimeMillis());
        PlayerState playerState = new PlayerState(0, "player1");
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
        int points = 0;
        if (parts[0].contains("gi")) {
            points = GENERAL_INFO_POINTS;
            type = GENERAL_INFO;
        } else if (parts[0].contains("1")) {
            points = SEASON1_POINTS;
            type = SEASON1;
        } else if (parts[0].contains("2")) {
            points = SEASON2_POINTS;
            type = SEASON2;
        } else if (parts[0].contains("3")) {
            points = SEASON3_POINTS;
            type = SEASON3;
        } else if (parts[0].contains("4")) {
            points = SEASON4_POINTS;
            type = SEASON4;
        } else if (parts[0].contains("5")) {
            points = SEASON5_POINTS;
            type = SEASON5;
        } else if (parts[0].contains("6")) {
            points = SEASON6_POINTS;
            type = SEASON6;
        } else if (parts[0].contains("7")) {
            points = SEASON7_POINTS;
            type = SEASON7;
        } else if (parts[0].contains("gl")) {
            points = GLOBAL_POINTS;
            type = GLOBAL;
        } else if (parts[0].contains("w")) {
            points = WHO_SAID_POINTS;
            type = WHO_SAID;
        }
        text = parts[1];
        correct_answear = parts[2];
        answear1 = parts[3];
        answear2 = parts[4];
        System.out.println(text);
        answear3 = parts[5];
        Question question = new Question(0, text, type, answear1, answear2,
                answear3, correct_answear, points, 0);
        return question;
    }
}
