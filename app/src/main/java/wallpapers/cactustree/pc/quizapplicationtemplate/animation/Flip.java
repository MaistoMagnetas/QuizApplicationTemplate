package wallpapers.cactustree.pc.quizapplicationtemplate.animation;

import android.view.View;
import android.widget.TextView;

/**
 * Flip animation.
 */
public class Flip {

    public void flipTextView(final View view, final String textToFlip){
        //First flip part
        view.animate().withLayer()
                .rotationY(-90)
                .setDuration(300)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        if (view instanceof TextView)
                        {
                            TextView textView = (TextView) view;
                            textView.setText(textToFlip);
                        }
                        //Chaning the image from second flip part
                        view.setRotation(0);
                        view.animate().withLayer()
                                .rotationY(0)
                                .setDuration(300)
                                .start();
                    }
                });
    }
}
