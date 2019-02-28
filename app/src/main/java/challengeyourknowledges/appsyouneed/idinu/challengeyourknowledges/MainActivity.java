package challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.database.DatabaseData;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.database.DatabaseHandler;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.database.InitializeDatabase;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.game.CountdownActivity;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.rezultate.NoteActivity;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.rezultate.RezultateActivity;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.rules.RulesActivity;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.zonarelaxare.ZonaRelaxareActivity;

public class MainActivity extends AppCompatActivity {
    private Button playButton;
    private Button rulesButton;
    private Button zonaRelaxareButton;
    private Button noteButton;
    private TextView pointsTextView;
    private AdView mAdView;

    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
            return;
        }
        setContentView(R.layout.activity_main);

        databaseHandler = new DatabaseHandler(MainActivity.this);
        if (databaseHandler.getAllQuestions().size() < 1) {
            InitializeDatabase.initializeDatabase(databaseHandler, getApplicationContext());
        }
        playButton = (Button) findViewById(R.id.button_play);
        rulesButton = (Button) findViewById(R.id.button_rules);
        zonaRelaxareButton = (Button) findViewById(R.id.button_tips_tricks);
        noteButton = (Button) findViewById(R.id.button_rezultate);

        pointsTextView = (TextView) findViewById(R.id.points_text_view);

        if (DatabaseData.getPlayerState().getPoints() == 0) {
            pointsTextView.setText("0");
        } else {
            pointsTextView.setText("" + DatabaseData.getPlayerState().getPoints());
        }

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(), CountdownActivity.class);
                        startActivity(intent);
                    }
                }, 500);

            }
        });

        rulesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RulesActivity.class);
                startActivity(intent);
            }
        });

        zonaRelaxareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ZonaRelaxareActivity.class);
                startActivity(intent);
            }
        });

        noteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RezultateActivity.class);
                startActivity(intent);
            }
        });

        MobileAds.initialize(this, "ca-app-pub-6866181891454476~7520037577");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
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
