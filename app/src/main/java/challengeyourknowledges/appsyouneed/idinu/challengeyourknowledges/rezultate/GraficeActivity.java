package challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.rezultate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.MainActivity;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.R;

public class GraficeActivity extends AppCompatActivity {
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafice);
        backButton = findViewById(R.id.back_button);

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
        Intent intent = new Intent(GraficeActivity.this, RezultateActivity.class);
        startActivity(intent);
        finish();
    }
}
