package challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.game;

import android.animation.ValueAnimator;
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
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.R;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.MainActivity;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.database.DatabaseData;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.database.DatabaseHandler;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.model.Question;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.model.Rankings;

public class GameActivity extends AppCompatActivity {
    private Button back_button;
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
    private ImageView heart1;
    private ImageView heart2;
    private ImageView heart3;
    private ImageView boltImage;
    private TextView question;
    private TextView timer;
    private TextView pointsTextView;
    private ConstraintLayout constraintLayoutQuestionBox;
    private ValueAnimator boltValueAnimator;
    private ValueAnimator boltQuestionAnimator;
    private int numarDeIntrebari;
    private String materie;

    private List<Question> questions;
    private List<Question> questionsAnsweared;
    private Timer t;
    private TimerTask task;
    private int time;
    private int points;
    private String correctAnswear;
    private int lifes;
    private DatabaseHandler databaseHandler;
    private CountDownTimer countDownTimer;
    private boolean isBoltQuestion;
    private int boltQuestionsCorrect = 0;
    private int boltQuestions = 0;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("time", time);
        outState.putInt("points", points);
        outState.putInt("lifes", lifes);
        outState.putString("correctAnswear", correctAnswear);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        time = savedInstanceState.getInt("time");
        points = savedInstanceState.getInt("points");
        lifes = savedInstanceState.getInt("lifes");
        correctAnswear = savedInstanceState.getString("correctAnswear");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHandler = new DatabaseHandler(this);

        numarDeIntrebari = getIntent().getIntExtra("numarIntrebari", 0);
        materie = getIntent().getStringExtra("materie");

        back_button = (Button) findViewById(R.id.back_button);
        answear1 = (Button) findViewById(R.id.button_answear1);
        answear2 = (Button) findViewById(R.id.button_answear2);
        answear3 = (Button) findViewById(R.id.button_answear3);
        answear4 = (Button) findViewById(R.id.button_answear4);
        heart1 = (ImageView) findViewById(R.id.heart1);
        heart2 = (ImageView) findViewById(R.id.heart2);
        heart3 = (ImageView) findViewById(R.id.heart3);
        boltImage = (ImageView) findViewById(R.id.bolt_image_view);
        question = (TextView) findViewById(R.id.question_text_view);
        timer = (TextView) findViewById(R.id.timer);
        constraintLayoutQuestionBox = (ConstraintLayout) findViewById(R.id.constraint_layout_question);

        DatabaseData.getGame().setGames_number(DatabaseData.getGame().getGames_number() - 1);
        databaseHandler.modifyGameObject( DatabaseData.getGame().getGames_number(), DatabaseData.getGame().getPlayer_state_id());

        boltImage.setVisibility(View.INVISIBLE);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO dialog questioning if you are sure you want to exit
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        initData();
        startGame();
    }

    public void initData() {
        questions = DatabaseData.getQuestions();
        questions = questions.subList(0, numarDeIntrebari);
        Collections.shuffle(questions);
        questionsAnsweared = new ArrayList<>();
        points = 0;
        lifes = 3;
        time = 21;
        isBoltQuestion = false;
        this.countDownTimer = new CountDownTimer(time * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                String remaining = "" + millisUntilFinished / 1000;
                timer.setText(remaining);
            }
            public void onFinish() {
                looseLife(null);
            }
        };
    }

    public void startGame() {
        heart1.setVisibility(View.VISIBLE);
        heart2.setVisibility(View.VISIBLE);
        heart3.setVisibility(View.VISIBLE);
        loadQuestion();
        time = 21;
        //startTimer();
    }


    public void reloadGame() {
        loadQuestion();
        countDownTimer.start();
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
            if (rQuestion.getType().contains("fast")) {
                boltQuestions++;
                startBoltAnim();
            }
            question.setText(rQuestion.getText());
            List<String> answears = new ArrayList<>();
            answears.add(rQuestion.getAnswear1());
            answears.add(rQuestion.getAnswear2());
            answears.add(rQuestion.getAnswear3());
            answears.add(rQuestion.getCorrect_answear());
            this.correctAnswear = rQuestion.getCorrect_answear();
            Collections.shuffle(answears);
            String answear1Text = "1. " + answears.get(0);
            String answear2Text = "2. " + answears.get(1);
            String answear3Text = "3. " + answears.get(2);
            String answear4Text = "4. " + answears.get(3);
            answear1.setText(answear1Text);
            answear2.setText(answear2Text);
            answear3.setText(answear3Text);
            answear4.setText(answear4Text);

            // START TIMER
            countDownTimer.start();

            answear1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isBoltQuestion) {
                        stopBoltAnim();
                    }
                    if (answear1.getText().toString().contains(correctAnswear)) {
                        if (isBoltQuestion) {
                            boltQuestionsCorrect++;
                        }
                        points += rQuestion.getPoints();
                        pointsTextView.setText("" + points);
                        makeCorrectAnim(answear1);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                reloadGame();
                            }
                        }, 1800);
//                        reloadGame();
                    } else {
                        looseLife(answear1);
                    }
                }
            });

            answear2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isBoltQuestion) {
                        stopBoltAnim();
                    }
                    if (answear2.getText().toString().contains(correctAnswear)) {
                        if (isBoltQuestion) {
                            boltQuestionsCorrect++;
                        }
                        points += rQuestion.getPoints();
                        pointsTextView.setText("" + points);
                        makeCorrectAnim(answear2);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                reloadGame();
                            }
                        }, 1800);
//                        reloadGame();
                    } else {
                        looseLife(answear2);
                    }
                }
            });

            answear3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isBoltQuestion) {
                        stopBoltAnim();
                    }
                    if (answear3.getText().toString().contains(correctAnswear)) {
                        if (isBoltQuestion) {
                            boltQuestionsCorrect++;
                        }
                        points += rQuestion.getPoints();
                        pointsTextView.setText("" + points);
                        makeCorrectAnim(answear3);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                reloadGame();
                            }
                        }, 1800);
//                        reloadGame();
                    } else {
                        looseLife(answear3);
                    }
                }
            });

            answear4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isBoltQuestion) {
                        stopBoltAnim();
                    }
                    if (answear4.getText().toString().contains(correctAnswear)) {
                        if (isBoltQuestion) {
                            boltQuestionsCorrect++;
                        }
                        points += rQuestion.getPoints();
                        pointsTextView.setText("" + points);
                        makeCorrectAnim(answear4);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                reloadGame();
                            }
                        }, 1800);
//                        reloadGame();
                    } else {
                        looseLife(answear4);
                    }
                }
            });
        } else {
            //TODO NO MORE QUESTIONS, WHO GET'S HERE IS THE BOSS
        }
    }


   public void looseLife(Button answear) {
       Handler handler = new Handler();
       if (answear != null ){
           makeWrongAnim(answear);
       }

       if (isBoltQuestion) {
           stopBoltAnim();
       }

       if (lifes > 0) {
            lifes -= 1;
            if (lifes == 2) {
                heart3.setVisibility(View.INVISIBLE);
//                heart3.setImageResource(R.drawable.heartblack);
            }
            if (lifes == 1) {
                heart2.setVisibility(View.INVISIBLE);
//                heart3.setImageResource(R.drawable.heartblack);

            }
            if (lifes == 0) {
                heart1.setVisibility(View.INVISIBLE);
//                heart3.setImageResource(R.drawable.heartblack);
            }
           handler.postDelayed(new Runnable() {
               @Override
               public void run() {
                   reloadGame();
               }
           }, 1600);
//            reloadGame();
       } else {
           endGame();
       }
    }

    public void endGame() {
        Handler handler = new Handler();
        countDownTimer.cancel();

        //Update rankings with new score in database and databaseData
        Rankings ranking = new Rankings(0, points, 0);
        List<Rankings> rankings = DatabaseData.getRankings();
        rankings.add(ranking);
        databaseHandler.updateRankings(rankings);
        DatabaseData.setRankings(databaseHandler.getAllRankings());

        //TODO what should happen if game ends
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                makeAlertDialog();
            }
        }, 1600);

//        Intent intent = new Intent(getApplicationContext(), GameOverActivity.class);
//        intent.putExtra("score", points);
//        startActivity(intent);
    }

    public void makeWrongAnim(Button answear) {
        answear.setBackgroundResource(R.drawable.wrong_answear_transition);
        rocketAnimation = (AnimationDrawable) answear.getBackground();
        rocketAnimation.start();
    }

    public void makeCorrectAnim(Button answear) {
        answear.setBackgroundResource(R.drawable.correct_answear_anim);
        rocketAnimation = (AnimationDrawable) answear.getBackground();
        rocketAnimation.start();
    }

    public void makeAlertDialog() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(GameActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(GameActivity.this);
        }

        if (DatabaseData.getGame().getGames_number() > 0) {
            builder.setTitle("JOC TERMINAT")
                    .setMessage(R.string.retryMessage1 + points + R.string.retryMessage2)
                    .setPositiveButton(R.string.retry, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent countdownActivityIntent = new Intent(GameActivity.this, CountdownActivity.class);
                            startActivity(countdownActivityIntent);
                            finish();
                        }
                    })
                     .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent mainActivityIntent = new Intent(GameActivity.this, MainActivity.class);
                            startActivity(mainActivityIntent);
                            finish();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert);
        } else {
            builder.setTitle("JOC TERMINAT")
                    .setMessage(R.string.retryMessage1 + points + R.string.castigaJocuriMessage)
                    .setPositiveButton(R.string.castigaJocuri, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent mainActivityIntent = new Intent(GameActivity.this, MainActivity.class);
                            startActivity(mainActivityIntent);
                            finish();
                        }
                    })
                    .setNegativeButton(R.string.meniu, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent mainActivityIntent = new Intent(GameActivity.this, MainActivity.class);
                            startActivity(mainActivityIntent);
                            finish();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert);
            AlertDialog alertDialog = builder.create();
            alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            alertDialog.show();
        }
    }

    private void startBoltAnim() {
        boltImage.setVisibility(View.VISIBLE);
        time = 11;
        isBoltQuestion = true;
        //V1
        boltValueAnimator = ValueAnimator.ofArgb(R.drawable.style_answear_button, R.drawable.anim_bolt_answear_button);
        boltValueAnimator.setDuration(10 * 1000);
        boltValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                answear1.setBackgroundColor((int) animation.getAnimatedValue());
                answear2.setBackgroundColor((int) animation.getAnimatedValue());
                answear3.setBackgroundColor((int) animation.getAnimatedValue());
                answear4.setBackgroundColor((int) animation.getAnimatedValue());
            }
        });
        boltValueAnimator.start();

        boltQuestionAnimator = ValueAnimator.ofArgb(R.drawable.style_question_box, R.drawable.anim_question_box);
        boltQuestionAnimator.setDuration(10 * 1000);
        boltQuestionAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                constraintLayoutQuestionBox.setBackgroundColor((int) animation.getAnimatedValue());
            }
        });
        boltQuestionAnimator.start();


        // V2
                /*answear1.setBackgroundResource(R.drawable.anim_bolt_answear_button);
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
                boltQuestionBoxAnim.start();*/
    }

    private void stopBoltAnim() {
        boltImage.setVisibility(View.INVISIBLE);

        //V2
//          boltAnimationButton1.stop();
//          boltAnimationButton2.stop();
//          boltAnimationButton3.stop();
//          boltAnimationButton4.stop();
        answear1.setBackgroundResource(R.drawable.style_answear_button);
        answear2.setBackgroundResource(R.drawable.style_answear_button);
        answear3.setBackgroundResource(R.drawable.style_answear_button);
        answear4.setBackgroundResource(R.drawable.style_answear_button);
        constraintLayoutQuestionBox.setBackgroundResource(R.drawable.style_question_box);
        isBoltQuestion = false;
        time = 21;
    }

    @Override
    public void onBackPressed() {
        //TODO dialog questioning if you are sure you want to exit
        countDownTimer.cancel();
        Intent intent = new Intent(GameActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
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
