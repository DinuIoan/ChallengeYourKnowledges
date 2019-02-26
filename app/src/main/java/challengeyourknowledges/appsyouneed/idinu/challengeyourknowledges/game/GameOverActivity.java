package challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.game;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.text.DecimalFormat;

import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.MainActivity;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.R;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.database.DatabaseData;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.database.DatabaseHandler;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.model.Game;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.model.Nota;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.model.Rankings;


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
    private TextView conditiiDeStresTextView;
    private TextView conditiiNormaleTextView;
    private TextView afTextView;
    private TextView notaFinalaTextView;
    private DatabaseHandler databaseHandler;

    private AdView mAdView;

    private static DecimalFormat df2 = new DecimalFormat(".##");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        mAdView = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        databaseHandler = new DatabaseHandler(getApplicationContext());

        Bundle extras = getIntent().getExtras();
        boltQuestions = extras.getInt("boltQuestions", 0);
        boltQuestionsCorrect = extras.getInt("boltQuestionsCorrect", 0);
        normalQuestions = extras.getInt("normalQuestions", 0);
        normalQuestionsCorrect = extras.getInt("normalQuestionsCorrect", 0);
        afQuestions = extras.getInt("afQuestions", 0);
        afQuestionsCorrect = extras.getInt("afQuestionsCorrect", 0);
        materie = extras.getString("materie","");

        conditiiDeStresTextView = (TextView) findViewById(R.id.conditii_de_stres_text_view);
        conditiiNormaleTextView = (TextView) findViewById(R.id.intrebari_normale_text_view);
        afTextView = (TextView) findViewById(R.id.adevarat_fals_text_view);
        tipulMaterieiTextView = (TextView) findViewById(R.id.tipul_materiei_text_view);
        notaFinalaTextView = findViewById(R.id.nota_finala_text_view);

        backButton = (Button) findViewById(R.id.back_button);
        incearcaDinNouButton = (Button) findViewById(R.id.reincearca_button);
        meniuButton = (Button) findViewById(R.id.menu_button);


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

        populateTextViews();

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

    private void populateTextViews() {
        conditiiDeStresTextView.setText(boltQuestionsCorrect + "/" + boltQuestions);
        conditiiNormaleTextView.setText(normalQuestionsCorrect+ "/" + normalQuestions);
        afTextView.setText(afQuestionsCorrect + "/" + afQuestions);
        double notaFinala = 0.0;
        int correctAnswears = boltQuestionsCorrect + normalQuestionsCorrect + afQuestionsCorrect;
        if (correctAnswears == 0 ) {
            notaFinala = 0.0;
        } else {
            notaFinala = (correctAnswears) * 10 /
                    (boltQuestions + normalQuestions + afQuestions);
        }
        notaFinalaTextView.setText("" + notaFinala);
        Nota nota = new Nota();
        nota.setNota(notaFinala);
        nota.setPlayerStateId(0);
        databaseHandler.addNota(nota);
    }

    private boolean isPercentAchieved() {
        if (boltQuestions == 0) {
            if (normalQuestions == 0) {
                if (afQuestionsCorrect != 0 && (afQuestionsCorrect * 100.0f) / afQuestions >= 75) {
                    return true;
                }
            } else if (afQuestions == 0) {
                if (normalQuestionsCorrect != 0 && (normalQuestionsCorrect * 100.0f) / normalQuestions >= 75) {
                    return true;
                }
            } else {
                if (normalQuestionsCorrect != 0 && (normalQuestionsCorrect * 100.0f) / normalQuestions >= 75) {
                    if (afQuestionsCorrect != 0 && (afQuestionsCorrect * 100.0f) / afQuestions >= 75) {
                        return true;
                    }
                }
            }
        } else {
            if (normalQuestions == 0) {
                if (boltQuestionsCorrect != 0 && (boltQuestionsCorrect * 100.0f) / boltQuestions >= 75 ) {
                    if (afQuestionsCorrect != 0 && (afQuestionsCorrect * 100.0f) / afQuestions >= 75 ) {
                        return true;
                    }
                }
            } else if (afQuestions == 0) {
                if (boltQuestionsCorrect != 0 && (boltQuestionsCorrect * 100.0f) / boltQuestions >= 75) {
                    if (normalQuestionsCorrect != 0 && (normalQuestionsCorrect * 100.0f) / normalQuestions >= 75) {
                        return true;
                    }
                }
            } else {
                if (boltQuestionsCorrect != 0 && (boltQuestionsCorrect * 100.0f) / boltQuestions >= 75) {
                    if (normalQuestionsCorrect != 0 && (normalQuestionsCorrect* 100.0f) / normalQuestions>= 75 ) {
                        if (afQuestionsCorrect != 0 && (afQuestionsCorrect * 100.0f) / afQuestions >= 75 ) {
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
