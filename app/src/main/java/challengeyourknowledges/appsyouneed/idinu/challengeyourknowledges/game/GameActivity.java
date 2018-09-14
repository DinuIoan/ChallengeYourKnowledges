package challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.game;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.R;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.MainActivity;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.database.DatabaseData;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.database.DatabaseHandler;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.model.Question;

public class GameActivity extends AppCompatActivity {
    private Button answear1;
    private Button answear2;
    private Button answear3;
    private Button answear4;
    private AnimationDrawable rocketAnimation;
    private AnimationDrawable boltAnimationButton1;
    private AnimationDrawable boltAnimationButton2;
    private AnimationDrawable boltAnimationButton3;
    private AnimationDrawable boltAnimationButton4;
    private AnimationDrawable boltQuestionBoxAnim;
    private ImageView boltImage;
    private TextView question;
    private TextView timer;
    private TextView tipulMaterieiTextView;
    private ConstraintLayout constraintLayoutQuestionBox;
    private int numarDeIntrebari;
    private String materie;
    private Button backButton;
    private TextView numarIntrebariTextView;
    private ImageView clockImageview;
    private ConstraintLayout constraintLayoutAfAnswears;
    private ConstraintLayout constraintLayoutNormalAnswears;
    private Button adevaratButton;
    private Button falsButton;

    private List<Question> questions;
    private List<Question> questionsAnsweared;
    private int time;
    private int points;
    private String correctAnswear;
    private int lifes;
    private DatabaseHandler databaseHandler;
    private CountDownTimer countDownTimer;
    private CountDownTimer countDownTimerBolt;
    private CountDownTimer countDownTimerAf;
    private CountDownTimer countDownTimerCustom;
    private boolean isBoltQuestion;
    private boolean isAfQuestion;
    private boolean isCountDownTimerCustom = false;
    private int boltQuestionsCorrect = 0;
    private int boltQuestions = 0;
    private int normalQuestions = 0;
    private int normalQuestionsCorrect = 0;
    private int afQuestions = 0;
    private int afQuestionsCorrect = 0;
    private long secsUntilFinish = 0;

    private AdView mAdView;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        databaseHandler = new DatabaseHandler(this);

        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        mAdView = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        numarDeIntrebari = getIntent().getIntExtra("numarIntrebari", 0);
        materie = getIntent().getStringExtra("materie");
        if (numarDeIntrebari == 0) {
            numarDeIntrebari = 10;
        }

        answear1 = (Button) findViewById(R.id.button_answear1);
        answear2 = (Button) findViewById(R.id.button_answear2);
        answear3 = (Button) findViewById(R.id.button_answear3);
        answear4 = (Button) findViewById(R.id.button_answear4);
        adevaratButton = (Button) findViewById(R.id.adevarat_button);
        falsButton = (Button) findViewById(R.id.fals_button);
        backButton = (Button) findViewById(R.id.back_button);
        boltImage = (ImageView) findViewById(R.id.bolt_image_view);
        clockImageview = (ImageView) findViewById(R.id.clock_imageview);
        question = (TextView) findViewById(R.id.question_text_view);
        timer = (TextView) findViewById(R.id.timer);
        numarIntrebariTextView = (TextView) findViewById(R.id.numar_intrebari_textview);
        tipulMaterieiTextView = (TextView) findViewById(R.id.tipul_materiei_text_view);
        constraintLayoutQuestionBox = (ConstraintLayout) findViewById(R.id.constraint_layout_question);
        constraintLayoutAfAnswears = (ConstraintLayout) findViewById(R.id.constraint_layout_af_answear);
        constraintLayoutNormalAnswears = (ConstraintLayout) findViewById(R.id.constraint_layout_answear);

        constraintLayoutAfAnswears.setVisibility(View.INVISIBLE);

        answear1.setBackgroundResource(R.drawable.style_answear_button);
        answear2.setBackgroundResource(R.drawable.style_answear_button);
        answear3.setBackgroundResource(R.drawable.style_answear_button);
        answear4.setBackgroundResource(R.drawable.style_answear_button);
        adevaratButton.setBackgroundResource(R.drawable.style_af_answear);
        falsButton.setBackgroundResource(R.drawable.style_af_answear);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeAlertDialog();
            }
        });

//        DatabaseData.getGame().setGames_number(DatabaseData.getGame().getGames_number() - 1);
//        databaseHandler.modifyGameObject( DatabaseData.getGame().getGames_number(), DatabaseData.getGame().getPlayer_state_id());

        boltImage.setVisibility(View.INVISIBLE);
        numarIntrebariTextView.setText("" + numarDeIntrebari);

        initData();
        startGame();
    }

    public void initData() {
        DatabaseData.setQuestions(databaseHandler.getQuestionsByDomain(materie));
        List<Question> principalQuestions = DatabaseData.getQuestions();
        Collections.shuffle(principalQuestions);
        questions = new ArrayList<>();
        for (Question question: principalQuestions) {
            Question clonedQuestion = Question.clone(question);
            questions.add(clonedQuestion);
        }

        if (questions.size() > numarDeIntrebari) {
            questions = questions.subList(0, numarDeIntrebari);
        }
        questionsAnsweared = new ArrayList<>();
        time = 21;
        isBoltQuestion = false;
        isAfQuestion = false;

        if (materie.contains("limbaromana")) {
            tipulMaterieiTextView.setText("Limba romana");
        } else if (materie.contains("istorie")) {
            tipulMaterieiTextView.setText("Istorie");
        } else if (materie.contains("geografie")) {
            tipulMaterieiTextView.setText("Geografie");
        } else if(materie.contains("economie")) {
            tipulMaterieiTextView.setText("Economie");
        } else if (materie.contains("biologie")) {
            tipulMaterieiTextView.setText("Biologie");
        }

        this.countDownTimer = new CountDownTimer(21 * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                secsUntilFinish = millisUntilFinished / 1000;
                String remaining = "" + millisUntilFinished / 1000;
                timer.setText(remaining);
            }
            public void onFinish() {
                wrongAnswear(null);
            }
        };

        this.countDownTimerBolt = new CountDownTimer(11 * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                secsUntilFinish = millisUntilFinished / 1000;
                String remaining = "" + millisUntilFinished / 1000;
                timer.setText(remaining);
            }
            public void onFinish() {
                wrongAnswear(null);
            }
        };

        this.countDownTimerAf = new CountDownTimer(16 * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                secsUntilFinish = millisUntilFinished / 1000;
                String remaining = "" + millisUntilFinished / 1000;
                timer.setText(remaining);
            }
            public void onFinish() {
                wrongAnswear(null);
            }
        };


    }

    public void startGame() {
        loadQuestion();
        time = 21;
    }


    public void reloadGame() {
        backButton.setClickable(true);
        if (numarDeIntrebari == 0) {
            endGame();
        }
        this.numarDeIntrebari--;
        numarIntrebariTextView.setText("" + numarDeIntrebari);
        isAfQuestion = false;
        loadQuestion();
    }

    public void pauseGame() {

    }

    public void loadQuestion() {
        if (questions.size() != 0) {
            Random random = new Random();
            int maximum = questions.size();
            int randomQuestion = random.nextInt(maximum);
            final Question rQuestion = questions.remove(randomQuestion);
            questionsAnsweared.add(rQuestion);
            // ANIMATE BOLT QUESTION
            verifyType(rQuestion.getType());
            question.setText(rQuestion.getText());
            this.correctAnswear = rQuestion.getCorrect_answear();
            if (!isAfQuestion) {
                setAnswearsText(rQuestion);
                answear1.setOnClickListener(v -> {
                    if (isCountDownTimerCustom) {
                        isCountDownTimerCustom = false;
                        countDownTimerCustom.cancel();
                    }
                    if (!isBoltQuestion) {
                        countDownTimer.cancel();
                    }
                    if (answear1.getText().toString().contains(correctAnswear)) {
                        if (isBoltQuestion) {
                            stopBoltAnim();
                            boltQuestionsCorrect++;
                        } else {
                            normalQuestionsCorrect++;
                        }
                        makeCorrectAnim(answear1);
                        Handler handler = new Handler();
                        handler.postDelayed(() -> reloadGame(), 1200);
                    } else {
                        if (isBoltQuestion) {
                            stopBoltAnim();
                        }
                        wrongAnswear(answear1);
                    }
                });

                answear2.setOnClickListener(v -> {
                    if (isCountDownTimerCustom) {
                        isCountDownTimerCustom = false;
                        countDownTimerCustom.cancel();
                    }
                    if (!isBoltQuestion) {
                        countDownTimer.cancel();
                    }
                    if (answear2.getText().toString().contains(correctAnswear)) {
                        if (isBoltQuestion) {
                            stopBoltAnim();
                            boltQuestionsCorrect++;
                        } else {
                            normalQuestionsCorrect++;
                        }
                        makeCorrectAnim(answear2);
                        Handler handler = new Handler();
                        handler.postDelayed(() -> reloadGame(), 1200);
                    } else {
                        if (isBoltQuestion) {
                            stopBoltAnim();
                        }
                        wrongAnswear(answear2);
                    }
                });

                answear3.setOnClickListener(v -> {
                    if (isCountDownTimerCustom) {
                        isCountDownTimerCustom = false;
                        countDownTimerCustom.cancel();
                    }
                    if (!isBoltQuestion) {
                        countDownTimer.cancel();
                    }
                    if (answear3.getText().toString().contains(correctAnswear)) {
                        if (isBoltQuestion) {
                            stopBoltAnim();
                            boltQuestionsCorrect++;
                        } else {
                            normalQuestionsCorrect++;
                        }
                        makeCorrectAnim(answear3);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                reloadGame();
                            }
                        }, 1200);
                    } else {
                        if (isBoltQuestion) {
                            stopBoltAnim();
                        }
                        wrongAnswear(answear3);
                    }
                });

                answear4.setOnClickListener(v -> {
                    if (isCountDownTimerCustom) {
                        isCountDownTimerCustom = false;
                        countDownTimerCustom.cancel();
                    }
                    if (!isBoltQuestion) {
                        countDownTimer.cancel();
                    }
                    if (answear4.getText().toString().contains(correctAnswear)) {
                        if (isBoltQuestion) {
                            boltQuestionsCorrect++;
                            stopBoltAnim();
                        } else {
                            normalQuestionsCorrect++;
                        }
                        makeCorrectAnim(answear4);
                        Handler handler = new Handler();
                        handler.postDelayed(() -> reloadGame(), 1200);
                    } else {
                        if (isBoltQuestion) {
                            stopBoltAnim();
                        }
                        wrongAnswear(answear4);
                    }
                });
            } else {
                adevaratButton.setOnClickListener(v -> {
                    if (isCountDownTimerCustom) {
                        isCountDownTimerCustom = false;
                        countDownTimerCustom.cancel();
                    } else {
                        countDownTimerAf.cancel();
                    }
                    Handler handler = new Handler();
                    if (correctAnswear.toLowerCase().contains("adevarat")) {
                        afQuestionsCorrect++;
                        makeCorrectAnimAf(adevaratButton);
                        handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                reloadGame();
                            }
                        }, 1200);
                    } else {
                        wrongAnswear(adevaratButton);
                    }
                });

                falsButton.setOnClickListener(v -> {
                    if (isCountDownTimerCustom) {
                        isCountDownTimerCustom = false;
                        countDownTimerCustom.cancel();
                    } else {
                        countDownTimerAf.cancel();
                    }
                    Handler handler = new Handler();
                    if (correctAnswear.toLowerCase().contains("fals")) {
                        afQuestionsCorrect++;
                        makeCorrectAnimAf(falsButton);
                        handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                reloadGame();
                            }
                        }, 1200);
                    } else {
                        wrongAnswear(falsButton);
                    }
                });
            }
        } else {
            endGame();
        }
    }

    private void setAnswearsText(Question rQuestion) {
        List<String> answears = new ArrayList<>();
        answears.add(rQuestion.getAnswear1());
        answears.add(rQuestion.getAnswear2());
        answears.add(rQuestion.getAnswear3());
        answears.add(rQuestion.getCorrect_answear());
        Collections.shuffle(answears);
        String answear1Text = "1. " + answears.get(0);
        String answear2Text = "2. " + answears.get(1);
        String answear3Text = "3. " + answears.get(2);
        String answear4Text = "4. " + answears.get(3);
        answear1.setText(answear1Text);
        answear2.setText(answear2Text);
        answear3.setText(answear3Text);
        answear4.setText(answear4Text);
    }

    private void verifyType(String type) {
        if (type.contains("fast")) {
            boltQuestions++;
            stopAfStyle();
            startBoltAnim();
        } else if (type.contains("af")) {
            makeAfStyle();
            isAfQuestion = true;
            afQuestions++;
        } else {
            normalQuestions++;
            countDownTimer.start();
            stopAfStyle();
        }
    }


   public void wrongAnswear(Button answear) {
       Handler handler = new Handler();

       if (answear != null && !isAfQuestion){
           makeWrongAnim(answear);
       } else if(answear != null && isAfQuestion) {
           makeWrongAfAnim(answear);
       }
       if (answear == null) {
           reloadGame();
       } else {
           handler.postDelayed(() -> reloadGame(), 1200);
       }
    }

    public void endGame() {
        stopCountdownTimer();
        Intent intent = new Intent(GameActivity.this, GameOverActivity.class);
        intent.putExtra("boltQuestions", boltQuestions);
        intent.putExtra("boltQuestionsCorrect", boltQuestionsCorrect);
        intent.putExtra("normalQuestions", normalQuestions);
        intent.putExtra("normalQuestionsCorrect",normalQuestionsCorrect);
        intent.putExtra("afQuestions",afQuestions);
        intent.putExtra("afQuestionsCorrect",afQuestionsCorrect);
        intent.putExtra("materie", materie);
        startActivity(intent);
        finish();

    }

    public void makeWrongAnim(Button answear) {
        answear.setBackgroundResource(R.drawable.wrong_answear_transition);
        rocketAnimation = (AnimationDrawable) answear.getBackground();
        if (rocketAnimation.isRunning()) {
            rocketAnimation.stop();
        }
        rocketAnimation.start();
        backButton.setClickable(false);
    }

    public void makeCorrectAnim(Button answear) {
        answear.setBackgroundResource(R.drawable.correct_answear_anim);
        rocketAnimation = (AnimationDrawable) answear.getBackground();
        if (rocketAnimation.isRunning()) {
            rocketAnimation.stop();
        }
        rocketAnimation.start();
        backButton.setClickable(false);
    }

    public void makeCorrectAnimAf(Button answear) {
        answear.setBackgroundResource(R.drawable.anim_correct_af_answear);
        rocketAnimation = (AnimationDrawable) answear.getBackground();
        if (rocketAnimation.isRunning()) {
            rocketAnimation.stop();
        }
        rocketAnimation.start();
        backButton.setClickable(false);
    }

    public void makeWrongAfAnim(Button answear) {
        answear.setBackgroundResource(R.drawable.anim_wrong_af_answear);
        rocketAnimation = (AnimationDrawable) answear.getBackground();
        if (rocketAnimation.isRunning()) {
            rocketAnimation.stop();
        }
        rocketAnimation.start();
        backButton.setClickable(false);
    }

    public void makeAlertDialog() {
        if (isAfQuestion) {
            countDownTimerAf.cancel();
        }
        if (isBoltQuestion) {
            countDownTimerBolt.cancel();
        }
        if (isCountDownTimerCustom) {
            countDownTimerCustom.cancel();
        }
        if (!isCountDownTimerCustom && !isAfQuestion && !isBoltQuestion) {
            isCountDownTimerCustom = false;
            countDownTimer.cancel();
        }

        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(GameActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(GameActivity.this);
        }
        builder.setTitle("ATENTIE")
                .setMessage(R.string.estiSigur)
                .setPositiveButton(R.string.da, (dialog, which) -> {
                    if (DatabaseData.getPlayerState().getPoints() != 0) {
                        DatabaseData.getPlayerState().setPoints(DatabaseData.getPlayerState().getPoints() - 1);
                        databaseHandler.modifyPlayerStateObject(0,
                                DatabaseData.getPlayerState().getPoints(), "player1");
                    }
                    stopCountdownTimer();
                    Intent intent = new Intent(GameActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                })
                 .setNegativeButton(R.string.nu, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        initCountDownTimerCustom();
                        countDownTimerCustom.start();
                        isCountDownTimerCustom = true;
                        dialog.cancel();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert);
            AlertDialog alertDialog = builder.create();
            alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
    }

    private void startBoltAnim() {
        boltImage.setVisibility(View.VISIBLE);
        time = 11;
        isBoltQuestion = true;
        clockImageview.setImageResource(R.drawable.ic_access_alarms_yellow);

        boltImage.animate().scaleX(1.5f).scaleY(1.5f).setDuration(200);
        boltImage.animate().scaleX(1f).scaleY(1f).setDuration(200);
        boltImage.animate().scaleX(1.5f).scaleY(1.5f).setDuration(200);
        boltImage.animate().scaleX(1f).scaleY(1f).setDuration(200);

        // V2
        answear1.setBackgroundResource(R.drawable.anim_bolt_answear_button);
        answear2.setBackgroundResource(R.drawable.anim_bolt_answear_button);
        answear3.setBackgroundResource(R.drawable.anim_bolt_answear_button);
        answear4.setBackgroundResource(R.drawable.anim_bolt_answear_button);
        constraintLayoutQuestionBox.setBackgroundResource(R.drawable.anim_question_box);
        boltAnimationButton1 = (AnimationDrawable) answear1.getBackground();
        boltAnimationButton2 = (AnimationDrawable) answear2.getBackground();
        boltAnimationButton3 = (AnimationDrawable) answear3.getBackground();
        boltAnimationButton4 = (AnimationDrawable) answear4.getBackground();
        boltQuestionBoxAnim = (AnimationDrawable) constraintLayoutQuestionBox.getBackground();
        boltAnimationButton1.start();
        boltAnimationButton2.start();
        boltAnimationButton3.start();
        boltAnimationButton4.start();
        boltQuestionBoxAnim.start();
        countDownTimerBolt.start();
    }

    private void stopBoltAnim() {

        boltImage.setVisibility(View.INVISIBLE);
        clockImageview.setImageResource(R.drawable.ic_access_alarms_orange);
        boltImage.animate().cancel();

        //V2
        boltAnimationButton1.stop();
        boltAnimationButton2.stop();
        boltAnimationButton3.stop();
        boltAnimationButton4.stop();
        answear1.setBackgroundResource(R.drawable.style_answear_button);
        answear2.setBackgroundResource(R.drawable.style_answear_button);
        answear3.setBackgroundResource(R.drawable.style_answear_button);
        answear4.setBackgroundResource(R.drawable.style_answear_button);
        constraintLayoutQuestionBox.setBackgroundResource(R.drawable.style_question_box);
        isBoltQuestion = false;
        countDownTimerBolt.cancel();
    }

    public void makeAfStyle() {
        countDownTimerAf.start();
        clockImageview.setImageResource(R.drawable.ic_access_alarms_blue);
        constraintLayoutNormalAnswears.setVisibility(View.INVISIBLE);
        constraintLayoutAfAnswears.setVisibility(View.VISIBLE);
        constraintLayoutQuestionBox.setBackgroundResource(R.drawable.style_af_question_box);
    }

    public void stopAfStyle() {
        countDownTimerAf.cancel();
        clockImageview.setImageResource(R.drawable.ic_access_alarms_orange);
        constraintLayoutAfAnswears.setVisibility(View.INVISIBLE);
        constraintLayoutNormalAnswears.setVisibility(View.VISIBLE);
        constraintLayoutQuestionBox.setBackgroundResource(R.drawable.style_question_box);
    }

    public void initCountDownTimerCustom() {
        this.countDownTimerCustom = new CountDownTimer(secsUntilFinish * 1000, 1000) {

            @Override
            public void onTick(long l) {
                secsUntilFinish = l / 1000;
                String remaining = "" + l / 1000;
                timer.setText(remaining);
            }

            @Override
            public void onFinish() {
                wrongAnswear(null);
            }
        };
    }

    private void stopCountdownTimer() {
        if (isBoltQuestion) {
            countDownTimerBolt.cancel();
        } else if (isAfQuestion) {
            countDownTimerAf.cancel();
        } else if (isCountDownTimerCustom) {
            countDownTimerCustom.cancel();
        } else {
            countDownTimer.cancel();
        }
    }

    @Override
    public void onBackPressed() {
        makeAlertDialog();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        stopCountdownTimer();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
