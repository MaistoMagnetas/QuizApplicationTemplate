package wallpapers.cactustree.pc.quizapplicationtemplate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import wallpapers.cactustree.pc.quizapplicationtemplate.dialogs.AnimationDialogListener;
import wallpapers.cactustree.pc.quizapplicationtemplate.dialogs.SettingsPopup;

public class StartingActivity extends AppCompatActivity implements AnimationDialogListener {

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_LAST_ANSWER = "lastAnswer";
    private static final String LAST_ANSWER_DEFAULT = "";

    private static final int REQUEST_CODE_QUIZ = 1;
    protected static final String IS_ANIMATION_NEEDED = "isAnimationNeeded";

    private TextView startingTextViewLastAnswer;
    private String lastAnswer;
    private boolean isAnimationOn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);

        findViewById(R.id.starting_image_view);
        findViewById(R.id.starting_text_view_title);
        ImageView settingsButton = findViewById(R.id.settings);

        startingTextViewLastAnswer = findViewById(R.id.starting_text_view_lastanswer);
        loadLastAnswerAndSettings();

        Button startQuizButton = findViewById(R.id.starting_button_quiz);
        startQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz();
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentManager supportFragmentManager = getSupportFragmentManager();
                SettingsPopup popupMenu = new SettingsPopup(v.getContext(), v, isAnimationOn);
                popupMenu.showSettingsPopup(supportFragmentManager);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null && requestCode == REQUEST_CODE_QUIZ) {
            if (resultCode == RESULT_OK) {
                String lastAnswer = data.getStringExtra(QuizActivity.LAST_QUIZ_ANSWER);
                if (!this.lastAnswer.equals(lastAnswer)) {
                    updateLastAnswer(lastAnswer);
                }
            }
        }
    }

    private void startQuiz() {
        Intent intent = new Intent(StartingActivity.this, QuizActivity.class);
        intent.putExtra(IS_ANIMATION_NEEDED, isAnimationOn);
        startActivityForResult(intent, REQUEST_CODE_QUIZ);
    }

    private void loadLastAnswerAndSettings() {
        SharedPreferences preferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        lastAnswer = preferences.getString(KEY_LAST_ANSWER, LAST_ANSWER_DEFAULT);
        startingTextViewLastAnswer.setText(getString(R.string.last_answer, lastAnswer));
        isAnimationOn = preferences.getBoolean(IS_ANIMATION_NEEDED, true);
    }

    private void updateLastAnswer(String lastAnswer) {
        this.lastAnswer = lastAnswer;
        startingTextViewLastAnswer.setText(getString(R.string.last_answer, lastAnswer));

        SharedPreferences preferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_LAST_ANSWER, this.lastAnswer);
        editor.apply();
    }

    @Override
    public void onAnimationSettingsChanged(boolean isAnimationOn) {
        this.isAnimationOn = isAnimationOn;
        SharedPreferences preferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(IS_ANIMATION_NEEDED, this.isAnimationOn);
        editor.apply();
    }
}
