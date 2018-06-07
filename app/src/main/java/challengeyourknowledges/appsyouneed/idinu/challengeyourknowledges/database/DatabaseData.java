package challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.database;

import java.util.List;

import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.model.AppInfo;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.model.Game;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.model.PlayerState;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.model.Question;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.model.Rankings;

public class DatabaseData {
    private static PlayerState playerState;
    private static Game game;
    private static List<Question> questions;
    private static List<Rankings> rankings;
    private static AppInfo appInfo;

    public static PlayerState getPlayerState() {
        return playerState;
    }

    public static void setPlayerState(PlayerState playerState) {
        DatabaseData.playerState = playerState;
    }

    public static Game getGame() {
        return game;
    }

    public static void setGame(Game game) {
        DatabaseData.game = game;
    }

    public static List<Question> getQuestions() {
        return questions;
    }

    public static void setQuestions(List<Question> questions) {
        DatabaseData.questions = questions;
    }

    public static List<Rankings> getRankings() {
        return rankings;
    }

    public static void setRankings(List<Rankings> rankings) {
        DatabaseData.rankings = rankings;
    }

    public static AppInfo getAppInfo() {
        return appInfo;
    }

    public static void setAppInfo(AppInfo appInfo) {
        DatabaseData.appInfo = appInfo;
    }
}
