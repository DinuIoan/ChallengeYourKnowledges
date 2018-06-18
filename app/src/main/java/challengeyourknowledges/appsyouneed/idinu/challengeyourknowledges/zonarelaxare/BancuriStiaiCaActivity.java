package challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.zonarelaxare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.R;

public class BancuriStiaiCaActivity extends AppCompatActivity {
    private boolean isBancuri = false;
    private boolean isStiaiCa = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bancuri_stiai_ca);

        Bundle extras = getIntent().getExtras();
        String type = extras.getString("type", "");
        if (type.contains("bancuri")) {
            isBancuri = true;
        } else {
            isStiaiCa = true;
        }


    }
}
