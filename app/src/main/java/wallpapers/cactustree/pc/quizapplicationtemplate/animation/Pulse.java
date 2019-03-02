package wallpapers.cactustree.pc.quizapplicationtemplate.animation;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.widget.TextView;

/**
 * Pulse animation.
 */
public class Pulse {

    public void pulseTextView(TextView textView) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(
                textView,
                PropertyValuesHolder.ofFloat("scaleX", 1.2f),
                PropertyValuesHolder.ofFloat("scaleY", 1.2f));

        objectAnimator.setDuration(300);
        objectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        objectAnimator.setRepeatMode(ObjectAnimator.REVERSE);
        objectAnimator.start();
    }
}
