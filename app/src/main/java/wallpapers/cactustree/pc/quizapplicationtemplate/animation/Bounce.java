package wallpapers.cactustree.pc.quizapplicationtemplate.animation;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import wallpapers.cactustree.pc.quizapplicationtemplate.R;

/**
 * Bounce animation if want to implement more. Add interface. and implement.
 */
public class Bounce {

    public void startBounceAnimation(Context context, Button button) {
        startBounceAnimation(context, button, 0.07, 30);
    }

    @SuppressWarnings("All")
    public void startBounceAnimation(Context context, Button button, double amplitude, double frequency) {
        final Animation animation = AnimationUtils.loadAnimation(context, R.anim.bounce);
        BounceInterpolator interpolator = new BounceInterpolator(amplitude, frequency);
        animation.setInterpolator(interpolator);
        button.startAnimation(animation);
    }
}
