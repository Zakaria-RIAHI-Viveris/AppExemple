package com.viveris.appexemple.ui.view;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;

/**
 * This class manage the display of a loading overlay
 */
public class ProgressBarManager {

    /**
     * display/hide a loading overlay
     * @param isLoading to know if we display or hide the frame layout
     * @param progressBarHolder the pregress bar id
     */
    public void onLoaderStateChange(boolean isLoading, FrameLayout progressBarHolder) {
        if (progressBarHolder != null) {
            AlphaAnimation animation;
            if (isLoading) {
                animation = new AlphaAnimation(0f, 1f);
            } else {
                animation = new AlphaAnimation(1f, 0f);
            }
            animation.setDuration(200);
            progressBarHolder.setAnimation(animation);
            if (isLoading) {
                progressBarHolder.setVisibility(View.VISIBLE);
            } else {
                progressBarHolder.setVisibility(View.GONE);
            }
        }
    }
}
