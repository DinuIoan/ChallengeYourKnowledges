package challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.zonarelaxare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.R;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.database.DatabaseData;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.model.Banc;

public class BancuriStiaiCaActivity extends AppCompatActivity {
    private boolean isBancuri = false;
    private boolean isStiaiCa = false;
    private Button next;
    private Button back;
    private List<Banc> bancuri;
    private TextView infoTextView;
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bancuri_stiai_ca);

        Bundle extras = getIntent().getExtras();

        back = (Button) findViewById(R.id.back_info);
        next = (Button) findViewById(R.id.forward_button);
        infoTextView = (TextView) findViewById(R.id.info_text_view);

        String type = extras.getString("type", "");
        if (type.contains("bancuri")) {
            isBancuri = true;
        } else {
            isStiaiCa = true;
        }

        bancuri = DatabaseData.getBancuri();
        Collections.shuffle(bancuri);
        infoTextView.setText(bancuri.get(0).getText());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position == 0) {
                    //do nothing, last joke
                } else {
                    position -= 1;
                    infoTextView.setText(bancuri.get(position).getText());
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position += 1;
                if (position == bancuri.size()){
                    // last joke
                } else {
                    infoTextView.setText(bancuri.get(position).getText());
                }
            }
        });
    }
}
