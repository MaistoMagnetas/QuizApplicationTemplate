package wallpapers.cactustree.pc.quizapplicationtemplate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import wallpapers.cactustree.pc.quizapplicationtemplate.animation.Bounce;
import wallpapers.cactustree.pc.quizapplicationtemplate.animation.Flip;
import wallpapers.cactustree.pc.quizapplicationtemplate.database.QuizDbHelper;

public class QuizActivity extends AppCompatActivity {

    public static final String LAST_QUIZ_ANSWER = "lastQuizAnswer";

    private TextView questionTextView;
    private TextView questionCountTextView;
    private Button answerButton1;
    private Button answerButton2;
    private Button answerButton3;

    private int questionCounter;
    private int questionCountTotal;
    private Question currentQuestion;
    private List<Question> questionList;
    private String lastAnswer;

    private long backPressedTime;

    private Map<Question, Integer> userChoicesForQuestionsHolder = new HashMap<>();
    private Map<String, Integer> answerTargetCalculation;

    boolean isAnimationON = true;
    private Bounce bounce = new Bounce();
    private Flip flip = new Flip();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionTextView = findViewById(R.id.text_view_question);
        questionCountTextView = findViewById(R.id.text_view_question_count);
        answerButton1 = findViewById(R.id.answer_button1);
        answerButton2 = findViewById(R.id.answer_button2);
        answerButton3 = findViewById(R.id.answer_button3);
        isAnimationON = getIntent().getBooleanExtra(StartingActivity.IS_ANIMATION_NEEDED, true);

        QuizDbHelper dbHelper = new QuizDbHelper(this);
        questionList = dbHelper.getAllQuestions();
        questionCountTotal = questionList.size();
        Collections.shuffle(questionList);

        answerTargetCalculation = generateAnswerTargetMap();
        showNextQuestion();

        answerButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userChoicesForQuestionsHolder.put(currentQuestion, 1);
                showNextQuestion();
            }
        });

        answerButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userChoicesForQuestionsHolder.put(currentQuestion, 2);
                showNextQuestion();
            }
        });

        answerButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userChoicesForQuestionsHolder.put(currentQuestion, 3);
                showNextQuestion();
            }
        });
    }

    private Map<String, Integer> generateAnswerTargetMap() {
        List<String> answerTargets = QuizContract.AnswerTargets.getAnswerTargets();
        Map<String, Integer> answerTargetMap = new HashMap<>();
        for (String answerTarget : answerTargets) {
            answerTargetMap.put(answerTarget, 0);
        }
        return answerTargetMap;
    }

    private void showNextQuestion() {
        if (questionCounter < questionCountTotal) {
            currentQuestion = questionList.get(questionCounter);

            //questionTextView.setText(currentQuestion.getQuestion());
            answerButton1.setText(currentQuestion.getOption1());
            answerButton2.setText(currentQuestion.getOption2());
            answerButton3.setText(currentQuestion.getOption3());

            questionCounter++;
            questionCountTextView.setText(getString(R.string.question_counter, Integer.toString(questionCounter), Integer.toString(questionCountTotal)));

            if (isAnimationON) {
                animateOnShowNextQuestion(currentQuestion.getQuestion());
            }
        } else if (questionCountTotal > 0) { //No more questions. Quiz end.
            lastAnswer = getFinalAnswerFromScoreMap();
            showCalculateAnswerAnimations();
            finishQuiz(RESULT_OK);
        }
    }

    private void showCalculateAnswerAnimations() {
        Intent answerIntent;
        if (isAnimationON) {
            answerIntent = new Intent(QuizActivity.this, CalculatingScoreActivity.class);
        } else {
            answerIntent = new Intent(QuizActivity.this, AnswerActivity.class);
        }
        answerIntent.putExtra(LAST_QUIZ_ANSWER, lastAnswer);
        startActivity(answerIntent);
    }

    private void animateOnShowNextQuestion(String textToFlip) {
        bounce.startBounceAnimation(this, answerButton1);
        bounce.startBounceAnimation(this, answerButton2);
        bounce.startBounceAnimation(this, answerButton3);

        flip.flipTextView(questionTextView, textToFlip);
    }

    /**
     * Calculate score into answerTargetCalculation map.
     * Adds single point into map with answerTargets as strings if answer int is equals with pressed button int.
     */
    private void calculateScore() {
        Set<Question> questions = userChoicesForQuestionsHolder.keySet();
        for (Question question : questions) {
            Integer userChoiceNumber = userChoicesForQuestionsHolder.get(question);
            if (userChoiceNumber != null && question.getAnswerNum() == userChoiceNumber) {
                String answerTarget = question.getAnswerTarget();
                Integer answerTargetScore = answerTargetCalculation.get(answerTarget);
                if (answerTargetScore != null) {
                    int answerScore = answerTargetScore + 1;
                    answerTargetCalculation.put(answerTarget, answerScore);
                }
            }
        }
    }

    /**
     * Gets final answer from score map. Takes first member with random index. To avoid situations in which,
     * score is 0 or we have two with same answers.
     *
     * @return final answer which had most score.
     */
    private String getFinalAnswerFromScoreMap() {
        calculateScore();
        List<String> answerTargets = new ArrayList<>(answerTargetCalculation.keySet());
        Random random = new Random();
        int randomNum = random.nextInt(answerTargets.size());
        String initialAnswerString = answerTargets.get(randomNum); // this gets rid of answers which are equal or 0 score.
        Integer initialAnswerScore = answerTargetCalculation.get(initialAnswerString);
        for (String answerTarget : answerTargets) {
            Integer checkingAnswerScore = answerTargetCalculation.get(answerTarget);
            if (checkingAnswerScore != null && initialAnswerScore != null &&
                    checkingAnswerScore > initialAnswerScore) {
                initialAnswerString = answerTarget;
                initialAnswerScore = checkingAnswerScore;
            }
        }
        return initialAnswerString;
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            finishQuiz(RESULT_CANCELED);
        } else {
            Toast.makeText(this, R.string.quiz_back_pressed, Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    private void finishQuiz(int resultCode) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(LAST_QUIZ_ANSWER, lastAnswer);
        setResult(resultCode, resultIntent);
        finish();
    }
}
