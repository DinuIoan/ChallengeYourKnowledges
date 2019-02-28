package challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.rezultate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.MainActivity;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.R;

public class RezultateActivity extends AppCompatActivity {
    private Button noteButton;
    private Button graficeButton;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezultate);

        noteButton = findViewById(R.id.button_note);
        graficeButton = findViewById(R.id.button_grafice);
        backButton = findViewById(R.id.back_button);

        noteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NoteActivity.class);
                startActivity(intent);
            }
        });

        graficeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GraficeActivity.class);
                startActivity(intent);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        backPressed();
    }

    private void backPressed() {
        Intent intent = new Intent(RezultateActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
