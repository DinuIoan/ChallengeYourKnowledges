package challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.game;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.MainActivity;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.R;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.database.DatabaseData;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.database.DatabaseHandler;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.model.Game;


public class GameOverActivity extends AppCompatActivity {
    private int boltQuestions = 0;
    private int boltQuestionsCorrect = 0;
    private int normalQuestions = 0;
    private int normalQuestionsCorrect = 0;
    private int afQuestions = 0;
    private int afQuestionsCorrect = 0;
    private TextView tipulMaterieiTextView;
    private String materie;
    private Button backButton;
    private Button incearcaDinNouButton;
    private Button meniuButton;
    private ProgressBar fastProgressBar;
    private ProgressBar normalProgressBar;
    private ProgressBar afProgressBar;
    private TextView conditiiDeStresTextView;
    private TextView conditiiNormaleTextView;
    private TextView afTextView;
    private TextView pointsTextView;
    private DatabaseHandler databaseHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        databaseHandler = new DatabaseHandler(getApplicationContext());

        Bundle extras = getIntent().getExtras();
        boltQuestions = extras.getInt("boltQuestions", 0);
        boltQuestionsCorrect = extras.getInt("boltQuestionsCorrect", 0);
        normalQuestions = extras.getInt("normalQuestions", 0);
        normalQuestionsCorrect = extras.getInt("normalQuestionsCorrect", 0);
        afQuestions = extras.getInt("afQuestions", 0);
        afQuestionsCorrect = extras.getInt("afQuestionsCorrect", 0);
        materie = extras.getString("materie","");

        conditiiDeStresTextView = (TextView) findViewById(R.id.conditii_stress_textview);
        conditiiNormaleTextView = (TextView) findViewById(R.id.conditii_normale_textview);
        afTextView = (TextView) findViewById(R.id.adevarat_fals_textview);
        tipulMaterieiTextView = (TextView) findViewById(R.id.tipul_materiei_text_view);
        pointsTextView = (TextView) findViewById(R.id.points_text_view);

        backButton = (Button) findViewById(R.id.back_button);
        incearcaDinNouButton = (Button) findViewById(R.id.reincearca_button);
        meniuButton = (Button) findViewById(R.id.menu_button);

        fastProgressBar = (ProgressBar) findViewById(R.id.progressBar_stress);
        normalProgressBar = (ProgressBar) findViewById(R.id.progressBar_normal);
        afProgressBar = (ProgressBar) findViewById(R.id.progressBar_adevarat_fals);
        populateProgressBars();



        if (DatabaseData.getPlayerState().getPoints() == 0) {
            pointsTextView.setText("0");
        } else {
            pointsTextView.setText("" + DatabaseData.getPlayerState().getPoints());
        }



        if (materie.contains("limbaromana")) {
            tipulMaterieiTextView.setText("Limba romana");
        } else if (materie.contains("istorie")) {
            tipulMaterieiTextView.setText("Istorie");
        } else if (materie.contains("geografie")) {
            tipulMaterieiTextView.setText("Geografie");
        } else if(materie.contains("economie")) {
            tipulMaterieiTextView.setText("Economie");
        } else if (materie.contains("biologie")) {
            tipulMaterieiTextView.setText("Biologie");
        }

        if (isPercentAchieved()) {
            DatabaseData.getPlayerState().setPoints(DatabaseData.getPlayerState().getPoints() + 1);
            databaseHandler.modifyPlayerStateObject(0, DatabaseData.getPlayerState().getPoints(), "player1");
        }
        pointsTextView.setText("" + DatabaseData.getPlayerState().getPoints());

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameOverActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        incearcaDinNouButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameOverActivity.this, CountdownActivity.class);
                startActivity(intent);
                finish();
            }
        });

        meniuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameOverActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    public void populateProgressBars() {
        if (boltQuestions == 0) {
            fastProgressBar.setMax(1);
            fastProgressBar.setProgress(1);
        } else {
            fastProgressBar.setMax(boltQuestions);
            fastProgressBar.setProgress(boltQuestionsCorrect);
        }
        if (normalQuestions == 0) {
            normalProgressBar.setMax(1);
            normalProgressBar.setProgress(1);
        } else {
            normalProgressBar.setMax(normalQuestions);
            normalProgressBar.setProgress(normalQuestionsCorrect);
        }
        if (afQuestions == 0) {
            afProgressBar.setMax(1);
            afProgressBar.setProgress(1);
        } else {
            afProgressBar.setMax(afQuestions);
            afProgressBar.setProgress(afQuestionsCorrect);
        }

        if (boltQuestions != 0 ) {
            if (boltQuestionsCorrect != 0) {
                conditiiDeStresTextView.setText("Conditii de stres: " + (boltQuestionsCorrect * 100) / boltQuestions + "%");
            } else {
                conditiiDeStresTextView.setText("Conditii de stres: 0%");
            }
        } else {
            conditiiDeStresTextView.setText("Conditii de stres: 0/0");
        }
        if (normalQuestions != 0 ) {
            if ( normalQuestionsCorrect != 0) {
                conditiiNormaleTextView.setText("Conditii normale: " + (normalQuestionsCorrect * 100.0f) / normalQuestions + "%");
            } else {
                conditiiNormaleTextView.setText("Conditii normale: 0%");
            }
        } else {
            conditiiNormaleTextView.setText("Conditii normale: 0/0");
        }
        if (afQuestions != 0) {
            if (afQuestionsCorrect != 0) {
                afTextView.setText("Adevarat / Fals: " + (afQuestionsCorrect * 100.0f) / afQuestions + "%");
            } else {
                afTextView.setText("Adevarat / Fals: 0%");
            }
        } else {
            afTextView.setText("Adevarat / Fals: 0/0");
        }
    }

    private boolean isPercentAchieved() {
        if (boltQuestions == 0) {
            if (normalQuestions == 0) {
                if (afQuestionsCorrect != 0 && (afQuestionsCorrect * 100.0f) / afQuestions > 75) {
                    return true;
                }
            } else if (afQuestions == 0) {
                if (normalQuestionsCorrect != 0 && (normalQuestionsCorrect * 100.0f) / normalQuestions > 75) {
                    return true;
                }
            } else {
                if (normalQuestionsCorrect != 0 && (normalQuestionsCorrect * 100.0f) / normalQuestions > 75) {
                    if (afQuestionsCorrect != 0 && (afQuestionsCorrect * 100.0f) / afQuestions > 75) {
                        return true;
                    }
                }
            }
        } else {
            if (normalQuestions == 0) {
                if (boltQuestionsCorrect != 0 && (boltQuestionsCorrect * 100.0f) / boltQuestions > 75 ) {
                    if (afQuestionsCorrect != 0 && (afQuestionsCorrect * 100.0f) / afQuestions > 75 ) {
                        return true;
                    }
                }
            } else if (afQuestions == 0) {
                if (boltQuestionsCorrect != 0 && (boltQuestionsCorrect * 100.0f) / boltQuestions > 75 ) {
                    if (normalQuestionsCorrect != 0 && (normalQuestionsCorrect * 100.0f) / normalQuestions > 75) {
                        return true;
                    }
                }
            } else {
                if (boltQuestionsCorrect != 0 && (boltQuestionsCorrect * 100.0f) / boltQuestions > 75 ) {
                    if (normalQuestionsCorrect != 0 && (normalQuestionsCorrect* 100.0f) / normalQuestions> 75 ) {
                        if (afQuestionsCorrect != 0 && (afQuestionsCorrect * 100.0f) / afQuestions > 75 ) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(GameOverActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
