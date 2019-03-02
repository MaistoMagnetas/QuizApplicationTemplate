package wallpapers.cactustree.pc.quizapplicationtemplate.dialogs;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import wallpapers.cactustree.pc.quizapplicationtemplate.R;

/**
 * Settings popup dialog.
 */
public class SettingsPopup extends PopupMenu {

    private boolean isAnimationOn;

    public SettingsPopup(Context context, View anchor, boolean isAnimationOn) {
        super(context, anchor);
        this.isAnimationOn = isAnimationOn;
    }

    public void showSettingsPopup(final FragmentManager supportFragmentManager){
        this.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_item1:
                        AnimationDialog animationDialog = new AnimationDialog(isAnimationOn);
                        animationDialog.show(supportFragmentManager, "animation dialog tag");
                        return true;
                    case R.id.menu_item2:
                        AboutDialog aboutDialog = new AboutDialog();
                        aboutDialog.show(supportFragmentManager, "about dialog tag");
                        return true;
                    default:
                        return false;
                }
            }
        });
        this.inflate(R.menu.popupmenu);
        this.show();
    }
}
