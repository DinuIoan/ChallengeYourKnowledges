package challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.game;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;

import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.MainActivity;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.R;

public class CountdownActivity extends AppCompatActivity {
    private CountDownTimer countDownTimer;
    private int numarIntrebari;
    private String materieSelectata;
    private Button incepeButton;
    private boolean materiaafostSelectata;
    private boolean numarulDeIntrebariAFostSelectat;
    private TextView countdownTextView;
    private Spinner numarIntrebariSpinner;
    private Spinner materieSpinner;
    private Button backButton;
    private int count = 3;
    private boolean countdownTimerStarted = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);
        incepeButton = (Button) findViewById(R.id.incepe_button);
        countdownTextView = (TextView) findViewById(R.id.countdown_timer_text);
        numarIntrebariSpinner = (Spinner) findViewById(R.id.spinnerNumarDeIntrebari);
        materieSpinner = (Spinner)findViewById(R.id.spinnerMaterie);
        backButton = (Button) findViewById(R.id.back_button);

        numarulDeIntrebariAFostSelectat = false;
        materiaafostSelectata = false;



        ArrayAdapter<CharSequence> staticAdapterMaterii = ArrayAdapter.createFromResource(this,
                R.array.materii, android.R.layout.simple_spinner_item);
        staticAdapterMaterii.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        materieSpinner.setAdapter(staticAdapterMaterii);
        materieSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                materiaafostSelectata = true;
                if (position == 0) {
                    materieSelectata = "limbaromana";
                } else if(position == 1) {
                    materieSelectata = "istorie";
                } else if (position == 2) {
                    materieSelectata = "geografie";
                } else if(position == 3) {
                    materieSelectata = "economie";
                } else if (position == 4) {
                    materieSelectata = "biologie";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        ArrayAdapter<CharSequence> staticAdapterNumarIntrebari = ArrayAdapter.createFromResource(this,
                R.array.numarIntrebari, android.R.layout.simple_spinner_item);
        staticAdapterNumarIntrebari.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        numarIntrebariSpinner.setAdapter(staticAdapterNumarIntrebari);
        numarIntrebariSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                numarulDeIntrebariAFostSelectat = true;
                if (position == 0) {
                    numarIntrebari = 10;
                } else if (position == 1) {
                    numarIntrebari = 20;
                } else if (position == 2) {
                    numarIntrebari = 30;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        this.countDownTimer = new CountDownTimer(4000, 100) {
            public void onTick(long millisUntilFinished) {
                String remaining = "" + millisUntilFinished / 1000;
                if (!remaining.contains("0")) {
                    countdownTextView.setText(remaining);
                }
            }

            public void onFinish() {
                countdownTimerStarted = false;
                Intent intent = new Intent(CountdownActivity.this, GameActivity.class);
                intent.putExtra("numarIntrebari", numarIntrebari);
                intent.putExtra("materie", materieSelectata);
                startActivity(intent);
                finish();
            }
        };

        incepeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incepeButton.setEnabled(false);
                materieSpinner.setEnabled(false);
                numarIntrebariSpinner.setEnabled(false);
                backButton.setEnabled(false);
                countDownTimer.start();
                countdownTimerStarted = true;

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                Intent intent = new Intent(CountdownActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (!countdownTimerStarted) {
            countDownTimer.cancel();
            Intent intent = new Intent(CountdownActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
