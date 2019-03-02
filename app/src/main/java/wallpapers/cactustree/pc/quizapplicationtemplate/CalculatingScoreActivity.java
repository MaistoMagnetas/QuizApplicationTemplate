package wallpapers.cactustree.pc.quizapplicationtemplate;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import wallpapers.cactustree.pc.quizapplicationtemplate.animation.Pulse;
import wallpapers.cactustree.pc.quizapplicationtemplate.animation.Rotation;

public class CalculatingScoreActivity extends AppCompatActivity {

    private static final int CALCULATE_TIME = 2500;
    private String lastAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculating_score);

        ImageView calculateScoreImageView = findViewById(R.id.calculatingScoreImage);
        TextView calculateScoreTextView = findViewById(R.id.calculatingScoreText);
        lastAnswer = getIntent().getStringExtra(QuizActivity.LAST_QUIZ_ANSWER);
        startPulse(calculateScoreTextView);
        startRotation(calculateScoreImageView);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(CalculatingScoreActivity.this, AnswerActivity.class);
                intent.putExtra(QuizActivity.LAST_QUIZ_ANSWER, lastAnswer);
                startActivity(intent);
                finish();
            }
        }, CALCULATE_TIME);
    }

    private void startPulse(TextView textView) {
        Pulse pulse = new Pulse();
        pulse.pulseTextView(textView);
    }

    private void startRotation(ImageView imageView) {
        Rotation rotation = new Rotation();
        rotation.rotateImageView(imageView);
    }
}
