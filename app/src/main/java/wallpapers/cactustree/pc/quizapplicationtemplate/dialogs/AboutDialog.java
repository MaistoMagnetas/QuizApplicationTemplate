package wallpapers.cactustree.pc.quizapplicationtemplate.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialogFragment;

import wallpapers.cactustree.pc.quizapplicationtemplate.R;

/**
 * About dialog with some information for user.
 */
public class AboutDialog extends AppCompatDialogFragment {

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.about_title)
                .setMessage(R.string.about_description)
                .setPositiveButton(R.string.about_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                             // Do nothing here.
                    }
                });
        return builder.create();
    }
}
