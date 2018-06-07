package challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.game;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.MainActivity;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.R;

public class CountdownActivity extends AppCompatActivity {
    private ImageView red3;
    private ImageView red2;
    private ImageView green1;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);
        red3 = (ImageView) findViewById(R.id.red3);
        red2 = (ImageView) findViewById(R.id.red2);
        green1 = (ImageView) findViewById(R.id.green1);

        green1.setVisibility(View.INVISIBLE);
        red2.setVisibility(View.INVISIBLE);
        red3.setVisibility(View.INVISIBLE);

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
                startActivity(intent);
            }
        };
        countDownTimer.start();
    }

    @Override
    public void onBackPressed() {
        countDownTimer.cancel();
        Intent intent = new Intent(CountdownActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
