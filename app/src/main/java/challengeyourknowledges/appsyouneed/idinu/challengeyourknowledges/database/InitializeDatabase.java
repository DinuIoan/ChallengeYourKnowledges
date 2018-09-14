package challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.database;


import android.content.Context;
import android.content.res.Resources;
import android.provider.ContactsContract;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.R;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.model.AppInfo;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.model.Banc;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.model.Game;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.model.PlayerState;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.model.Question;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.model.StiaiCa;

public class InitializeDatabase {
    private static DatabaseHandler databaseHandler;
    private static Resources resources;

    public static void initializeDatabase(DatabaseHandler db, Context context) {
        databaseHandler = db;
        resources = context.getResources();

        initializeQuestions();
        initializeBancuri();
        initializeStiaiCa();
        initializeAppInfo();
        initializeGame();
        initializePlayerState();
    }

    private static void initializeQuestions() {
        InputStream inputStream = resources.openRawResource(R.raw.input);
        if (databaseHandler.getAllQuestions().size() == 0) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split("/");
                    if (!parts[0].contains("#")) {
                        Question question = makeQuestion(parts);
                        databaseHandler.addQuestion(question);
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void initializeAppInfo() {
        AppInfo appInfo = new AppInfo(0L, System.currentTimeMillis());
        if (databaseHandler.getAppInfo() == null) {
            databaseHandler.addAppInfo(appInfo);
        }
    }

    private static void initializePlayerState() {
        PlayerState playerState = new PlayerState(0,0,  "player1");
        if (databaseHandler.getAllPlayerState().size() == 0) {
            databaseHandler.addPlayerState(playerState);
        }
    }

    private static void initializeGame() {
        Game game = new Game(0, 7, 0);
        if (databaseHandler.getAllGames().size() == 0) {
            databaseHandler.addGame(game);
        }
    }

    private static void initializeBancuri() {
        if (databaseHandler.getALLBancuri().size() == 0) {
            InputStream bancuriInputStream = resources.openRawResource(R.raw.bancuri);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(bancuriInputStream))) {
                String line;
                Banc banc = new Banc();
                banc.setText("");
                while ((line = br.readLine()) != null) {
                    if (line.contains("/")) {
                        databaseHandler.addBanc(banc);
                        banc.setText("");
                    } else {
                        banc.setText(banc.getText() + line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void initializeStiaiCa() {
        if (databaseHandler.getALLStiaiCa().size() ==0) {
            InputStream stiaicaInputStream = resources.openRawResource(R.raw.stiaica);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(stiaicaInputStream))) {
                String line;
                StiaiCa stiaiCa = new StiaiCa();
                stiaiCa.setText("");
                while ((line = br.readLine()) != null) {
                    if (line.contains("/")) {
                        databaseHandler.addStiaiCa(stiaiCa);
                        stiaiCa.setText("");
                    } else {
                        stiaiCa.setText(stiaiCa.getText() + line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
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
        if (parts[0].contains("af-")) {
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
            answear3 = parts[5];
        }
        Question question = new Question(0, text, type, answear1, answear2,
                answear3, correct_answear, domain, 0);
        return question;
    }

    private static String checkDomain(String type) {
        if (type.contains("limbaromana")){
            return "limbaromana";
        }
        if (type.contains("istorie")) {
            return "istorie";
        }
        if (type.contains("geografie")) {
            return "geografie";
        }
        if (type.contains("economie")) {
            return "economie";
        }
        if (type.contains("biologie")) {
           return "biologie";
        }
        return "";
    }

}
