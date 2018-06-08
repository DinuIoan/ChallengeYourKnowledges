package challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.R;
import com.facebook.stetho.Stetho;

import java.util.Date;

import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.database.DatabaseData;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.database.DatabaseHandler;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.database.InitializeDatabase;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.game.CountdownActivity;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.model.AppInfo;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.rules.RulesActivity;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.stats.StatsActivity;

public class MainActivity extends AppCompatActivity {
    private Button playButton;
    private Button statsButton;
    private Button rulesButton;

    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }
        setContentView(R.layout.activity_main);

        Stetho.initializeWithDefaults(this);

        databaseHandler = new DatabaseHandler(MainActivity.this);
        if (databaseHandler.getAllQuestions().size() < 1 ) {
            InitializeDatabase.initializeDatabase(databaseHandler, getApplicationContext());
        }
        playButton = (Button) findViewById(R.id.button_play);
        statsButton = (Button) findViewById(R.id.button_stats);
        rulesButton = (Button) findViewById(R.id.button_rules);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DatabaseData.getGame().getGames_number() == 0) {

                } else {
                    Intent intent = new Intent(getApplicationContext(), CountdownActivity.class);
                    startActivity(intent);
                }
            }
        });

        statsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StatsActivity.class);
                startActivity(intent);
            }
        });

        rulesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RulesActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
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
