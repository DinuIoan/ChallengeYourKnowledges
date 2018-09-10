package challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.splash;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.MainActivity;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.R;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.database.DatabaseData;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.database.DatabaseHandler;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.database.InitializeDatabase;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.game.CountdownActivity;

public class SplashActivity extends AppCompatActivity {
        private DatabaseHandler db;
        private CountDownTimer countDownTimer;

        @Override
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            // set the content view for your splash screen you defined in an xml file
            setContentView(R.layout.activity_splash);
            // perform other stuff you need to do
            this.countDownTimer = new CountDownTimer(2000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            };

            doInBackground();
        }

        protected Integer doInBackground(String... voids){
            // load your xml feed asynchronously
            db = new DatabaseHandler(SplashActivity.this);
            countDownTimer.start();
            if (db.getAllQuestions().size() < 1 ) {
                InitializeDatabase.initializeDatabase(db, SplashActivity.this);
            }

            if (DatabaseData.getQuestions() == null) {
                DatabaseData.setQuestions(db.getAllQuestions());
            }
            if (DatabaseData.getGame() == null) {
                DatabaseData.setGame(db.getAllGames().get(0));
            }
            if (DatabaseData.getPlayerState() == null) {
                DatabaseData.setPlayerState(db.getAllPlayerState().get(0));
            }
            if (DatabaseData.getRankings() == null) {
                DatabaseData.setRankings(db.getAllRankings());
            }
            if (DatabaseData.getAppInfo() == null ) {
                DatabaseData.setAppInfo(db.getAppInfo());
            }
            if (DatabaseData.getBancuri() == null) {
                DatabaseData.setBancuri(db.getALLBancuri());
            }
            if (DatabaseData.getStiaiCa() == null) {
                DatabaseData.setStiaiCa(db.getALLStiaiCa());
            }
            return 1234;
        }

    @Override
    public void onBackPressed() {
        countDownTimer.cancel();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}