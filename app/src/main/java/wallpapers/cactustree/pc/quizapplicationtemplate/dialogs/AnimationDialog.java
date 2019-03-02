package wallpapers.cactustree.pc.quizapplicationtemplate.dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialogFragment;

import wallpapers.cactustree.pc.quizapplicationtemplate.R;

/**
 * Animation settings dialog.
 */
@SuppressLint("ValidFragment")
public class AnimationDialog extends AppCompatDialogFragment {

    private AnimationDialogListener listener;
    private String[] choiceArray = new String[]{"Animation ON", "Animation OFF"};
    private boolean isAnimationON;

    public AnimationDialog(boolean isAnimationON) {
        this.isAnimationON = isAnimationON;
    }

    @NonNull //just for green code.
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.animation_title)
                .setPositiveButton(R.string.animation_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onAnimationSettingsChanged(isAnimationON);
                    }
                })
                .setSingleChoiceItems(choiceArray, getCheckedRadioButton(), new DialogInterface
                        .OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        isAnimationON = item == 0;
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (AnimationDialogListener) context;
        } catch (ClassCastException ex) {
            throw new ClassCastException("Exception on animation button change: " + ex.toString());
        }
    }

    private int getCheckedRadioButton() {
        if (isAnimationON) {
            return 0;
        }
        return 1;
    }
}
