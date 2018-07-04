package challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.tipsTricks;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.MainActivity;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.R;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.zonarelaxare.ZonaRelaxareActivity;

public class TipsAndTricksActivity extends AppCompatActivity {
    private TextView tipsTricksTextView;
    private static final String FILEPATH = "tips.txt";
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips_tricks);

        tipsTricksTextView = (TextView) findViewById(R.id.tips_tricks_text_view);
        backButton = (Button) findViewById(R.id.back_button);
        StringBuilder text = new StringBuilder();

        File inputQuestions = new File(FILEPATH);
        final Resources resources = getApplicationContext().getResources();
        InputStream inputStream = resources.openRawResource(R.raw.tips);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        tipsTricksTextView.setText(text);
        tipsTricksTextView.setMovementMethod(new ScrollingMovementMethod());

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ZonaRelaxareActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TipsAndTricksActivity.this, ZonaRelaxareActivity.class);
        startActivity(intent);
        finish();
    }
}
