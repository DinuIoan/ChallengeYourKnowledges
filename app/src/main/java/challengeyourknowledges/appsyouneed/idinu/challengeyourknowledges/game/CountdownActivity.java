package challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.game;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.MainActivity;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.R;

public class CountdownActivity extends AppCompatActivity {
    private ImageView red3;
    private ImageView red2;
    private ImageView green1;
    private CountDownTimer countDownTimer;
    private Spinner numarIntrebariSpinner;
    private Spinner materieSpinner;
    private int numarIntrebari;
    private String materieSelectata;
    private Button incepeButton;
    private boolean materiaafostSelectata;
    private boolean numarulDeIntrebariAFostSelectat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);
        red3 = (ImageView) findViewById(R.id.red3);
        red2 = (ImageView) findViewById(R.id.red2);
        green1 = (ImageView) findViewById(R.id.green1);
        incepeButton = (Button) findViewById(R.id.incepe_button);

        green1.setVisibility(View.INVISIBLE);
        red2.setVisibility(View.INVISIBLE);
        red3.setVisibility(View.INVISIBLE);

        numarulDeIntrebariAFostSelectat = false;
        materiaafostSelectata = false;

        numarIntrebariSpinner = (Spinner) findViewById(R.id.static_spinner_nr_intrebari);
        materieSpinner = (Spinner) findViewById(R.id.static_spinner_materie);

        ArrayAdapter<CharSequence> staticAdapterNumarIntrebari = ArrayAdapter.createFromResource(this, R.array.numarIntrebari, R.layout.spinner_layout_item);
        staticAdapterNumarIntrebari.setDropDownViewResource(R.layout.spinner_layout_item);
        numarIntrebariSpinner.setAdapter(staticAdapterNumarIntrebari);
        numarIntrebariSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                numarulDeIntrebariAFostSelectat = true;
                if (position == 0) {
                    numarIntrebari = 10;
                } else if (position == 1) {
                    numarIntrebari = 25;
                } else if (position == 2) {
                    numarIntrebari = 50;
                }
            }
        });

        ArrayAdapter<CharSequence> staticAdapterMaterii = ArrayAdapter.createFromResource(this, R.array.materii, R.layout.spinner_layout_item);
        staticAdapterMaterii.setDropDownViewResource(R.layout.spinner_layout_item);
        materieSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
        });

        this.countDownTimer = new CountDownTimer(4 * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                String remaining = "" + millisUntilFinished / 1000;
                if (remaining.equals("3")) {
                    red3.setVisibility(View.VISIBLE);
                } else if (remaining.equals("2")) {
                    red2.setVisibility(View.VISIBLE);
                } else if (remaining.equals("1")) {
                    green1.setVisibility(View.VISIBLE);
                }
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
