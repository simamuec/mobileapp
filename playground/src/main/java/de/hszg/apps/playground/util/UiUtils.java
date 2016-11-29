package de.hszg.apps.playground.util;

import android.app.Activity;
import android.view.View;

/**
 * Created on 19.11.2016.
 */

public class UiUtils {

    public static <T extends View> T findView(View root, int id) {
        return (T) root.findViewById(id);
    }

    public static <T extends View> T findView(Activity activity, int id) {
        return (T) activity.getWindow().getDecorView().getRootView().findViewById(id);
    }
}
