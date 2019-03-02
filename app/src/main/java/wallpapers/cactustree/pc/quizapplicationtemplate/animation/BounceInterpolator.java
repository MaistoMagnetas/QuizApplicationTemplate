package wallpapers.cactustree.pc.quizapplicationtemplate.animation;

import android.view.animation.Interpolator;

/**
 * Bounce interpolator in make bounce effect smoother and more nice.
 */
public class BounceInterpolator implements Interpolator {

    private double amplitude;
    private double frequency;

    BounceInterpolator(double amplitude, double frequency) {
        this.amplitude = amplitude;
        this.frequency = frequency;
    }

    @Override
    public float getInterpolation(float time) {
        return (float) (-1 * Math.pow(Math.E, -time / amplitude) *
                Math.cos(frequency * time) + 1);
    }
}
