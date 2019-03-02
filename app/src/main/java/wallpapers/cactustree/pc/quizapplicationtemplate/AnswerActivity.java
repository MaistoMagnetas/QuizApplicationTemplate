package wallpapers.cactustree.pc.quizapplicationtemplate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AnswerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        findViewById(R.id.answer_imageview);
        String lastAnswer = getIntent().getStringExtra(QuizActivity.LAST_QUIZ_ANSWER);
        TextView answerTextView = findViewById(R.id.answer_textview);
        answerTextView.setText(getString(R.string.quiz_answer, lastAnswer));

        Button answerButton = findViewById(R.id.answer_confirm_button);
        answerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
