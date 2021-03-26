package top.woolensheep.emojiedit.hook;

import android.app.Activity;
import android.util.Log;

import de.robv.android.xposed.XC_MethodHook;

public class ActivityHook extends XC_MethodHook {

    /* Assure latest read of write */
    private static volatile Activity _currentActivity = null;

    public static Activity getCurrentActivity() {
        return _currentActivity;
    }

    @Override
    protected void afterHookedMethod(MethodHookParam param)
            throws Throwable {
        _currentActivity = (Activity) param.getResult();
        Log.v("activity", "Current Activity : " + _currentActivity.getClass().getName());
    }
}