package wallpapers.cactustree.pc.quizapplicationtemplate.animation;

import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

/**
 * Simple rotation animation.
 */
public class Rotation {

    public void rotateImageView(ImageView imageView) {
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(2500);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        imageView.startAnimation(rotateAnimation);
    }
}
