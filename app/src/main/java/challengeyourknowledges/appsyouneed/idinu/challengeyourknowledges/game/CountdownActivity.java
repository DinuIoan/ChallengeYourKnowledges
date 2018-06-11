package challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.game;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
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
                    materieSelectata = "Limba romana";
                } else if(position == 1) {
                    materieSelectata = "Logica";
                } else if (position == 2) {
                    materieSelectata = "Istorie";
                } else if (position == 3) {
                    materieSelectata = "Geografie";
                } else if (position == 4) {
                    materieSelectata = "Fizica";
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

        this.countDownTimer = new CountDownTimer(4 * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                String remaining = "" + millisUntilFinished / 1000;
                countdownTextView.setText(remaining);
            }
            public void onFinish() {
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
                if (materiaafostSelectata && numarulDeIntrebariAFostSelectat) {
                    countDownTimer.start();
                } else if (!materiaafostSelectata) {
                    createAlertDialog("materie");
                } else if (!numarulDeIntrebariAFostSelectat) {
                    createAlertDialog("numar");
                }
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

    private void createAlertDialog(String type) {
        String message = "";
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(CountdownActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(CountdownActivity.this);
        }

        if (type.equals("materie")) {
            message = getResources().getString(R.string.materieNeselectata);
        } else {
            message = getResources().getString(R.string.numarIntrebariNeselectat);
        }

        builder.setTitle("ATENTIE")
                .setMessage(message)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert);
    }

    @Override
    public void onBackPressed() {
        countDownTimer.cancel();
        Intent intent = new Intent(CountdownActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}
