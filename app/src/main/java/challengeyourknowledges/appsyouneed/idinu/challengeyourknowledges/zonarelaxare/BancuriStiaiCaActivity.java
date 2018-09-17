package challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.zonarelaxare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.MainActivity;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.R;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.database.DatabaseData;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.model.Banc;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.model.StiaiCa;

public class BancuriStiaiCaActivity extends AppCompatActivity {
    private boolean isBancuri = false;
    private boolean isStiaiCa = false;
    private Button backButton;
    private Button next;
    private Button back;
    private List<Banc> bancuri;
    private List<StiaiCa> stiaiCa;
    private TextView infoTextView;
    private TextView titlu;
    private int position = 0;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bancuri_stiai_ca);

        Bundle extras = getIntent().getExtras();

        back = (Button) findViewById(R.id.back_info);
        next = (Button) findViewById(R.id.forward_button);
        backButton = (Button) findViewById(R.id.back_button);
        infoTextView = (TextView) findViewById(R.id.info_text_view);
        titlu = (TextView) findViewById(R.id.titlu);
        infoTextView.setMovementMethod(new ScrollingMovementMethod());
        infoTextView.setGravity(9);

        String type = extras.getString("type", "");
        if (type.contains("bancuri")) {
            titlu.setText("Bancuri");
            isBancuri = true;
        } else {
            titlu.setText("Stiai ca...");
            isStiaiCa = true;
        }

        if (isBancuri) {
            bancuri = DatabaseData.getBancuri();
            if (bancuri != null && bancuri.size() !=0 ) {
                Collections.shuffle(bancuri);
                infoTextView.setText(bancuri.get(0).getText());
            }
        } else {
            stiaiCa = DatabaseData.getStiaiCa();
            if (stiaiCa != null && stiaiCa.size() != 0) {
                Collections.shuffle(stiaiCa);
                infoTextView.setText(stiaiCa.get(0).getText());
            }
        }


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position == 0) {
                    //do nothing, last joke
                } else {
                    position -= 1;
                    if (isBancuri) {
                        infoTextView.setText(bancuri.get(position).getText());
                    } else {
                        infoTextView.setText(stiaiCa.get(position).getText());
                    }
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBancuri && position == bancuri.size() - 1){
                    // last joke
                } else {
                    position += 1;
                    if (isBancuri) {
                        infoTextView.setText(bancuri.get(position).getText());
                    } else {
                        if (stiaiCa != null && position == stiaiCa.size()) {

                        } else {
                            infoTextView.setText(stiaiCa.get(position).getText());
                        }
                    }
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BancuriStiaiCaActivity.this, ZonaRelaxareActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(BancuriStiaiCaActivity.this, ZonaRelaxareActivity.class);
        startActivity(intent);
        finish();
    }
}
