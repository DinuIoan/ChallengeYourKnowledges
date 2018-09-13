package challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.zonarelaxare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.MainActivity;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.R;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.tipsTricks.TipsAndTricksActivity;

public class ZonaRelaxareActivity extends AppCompatActivity {
    private Button buttonBancuri;
    private Button buttonStiaiCa;
    private Button buttonTipsTricks;
    private Button backButton;
    private Button backInfo;
    private Button forwardInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zona_relaxare);

        buttonBancuri = (Button) findViewById(R.id.button_bancuri);
        buttonStiaiCa = (Button) findViewById(R.id.button_stiai_ca);
        buttonTipsTricks = (Button) findViewById(R.id.button_tips_tricks);
        backButton = (Button) findViewById(R.id.back_button);
        backInfo = (Button) findViewById(R.id.back_info);
        forwardInfo = (Button) findViewById(R.id.forward_button);

        buttonBancuri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BancuriStiaiCaActivity.class);
                intent.putExtra("type", "bancuri");
                startActivity(intent);
                finish();
            }
        });

        buttonStiaiCa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BancuriStiaiCaActivity.class);
                intent.putExtra("type", "stiaica");
                startActivity(intent);
                finish();
            }
        });

        buttonTipsTricks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TipsAndTricksActivity.class);
                startActivity(intent);
                finish();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ZonaRelaxareActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
