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


public class GameOverActivity extends AppCompatActivity {
    private int boltQuestions = 0;
    private int boltQuestionsCorrect = 0;
    private int normalQuestions = 0;
    private int normalQuestionsCorrect = 0;
    private int afQuestions = 0;
    private int afQuestionsCorrect = 0;
    private Button backButton;
    private Button incearcaDinNouButton;
    private Button meniuButton;
    private ProgressBar fastProgressBar;
    private ProgressBar normalProgressBar;
    private ProgressBar afProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        Bundle extras = getIntent().getExtras();
        boltQuestions = extras.getInt("boltQuestions", 0);
        boltQuestionsCorrect = extras.getInt("boltQuestionsCorrect", 0);
        normalQuestions = extras.getInt("normalQuestions", 0);
        normalQuestionsCorrect = extras.getInt("normalQuestionsCorrect", 0);
        afQuestions = extras.getInt("afQuestions", 0);
        afQuestionsCorrect = extras.getInt("afQuestionsCorrect", 0);

        backButton = (Button) findViewById(R.id.back_button);
        incearcaDinNouButton = (Button) findViewById(R.id.reincearca_button);
        meniuButton = (Button) findViewById(R.id.menu_button);

        fastProgressBar = (ProgressBar) findViewById(R.id.progressBar_stress);
        normalProgressBar = (ProgressBar) findViewById(R.id.progressBar_normal);
        afProgressBar = (ProgressBar) findViewById(R.id.progressBar_adevarat_fals);
        populateProgressBars();

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
        fastProgressBar.setMax(boltQuestions);
        fastProgressBar.setProgress(boltQuestionsCorrect);

        normalProgressBar.setMax(normalQuestions);
        normalProgressBar.setProgress(normalQuestionsCorrect);

        afProgressBar.setMax(afQuestions);
        afProgressBar.setProgress(afQuestionsCorrect);
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
