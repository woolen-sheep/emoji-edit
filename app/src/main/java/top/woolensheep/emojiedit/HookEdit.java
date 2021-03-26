package top.woolensheep.emojiedit;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;

import top.woolensheep.emojiedit.hook.ActivityHook;
import top.woolensheep.emojiedit.hook.EmojiEdit;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.callMethod;

public class HookEdit implements IXposedHookLoadPackage {
    public static final boolean verbose = true;

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
//        verboseLog(this, "load", lpparam.packageName);
//        if (lpparam.packageName.equals("top.woolensheep.emojiedit")) return;
        Class<?> instrumentation = XposedHelpers.findClass(
                "android.app.Instrumentation", lpparam.classLoader);

        Method method = instrumentation.getMethod("newActivity",
                ClassLoader.class, String.class, Intent.class);

        XposedBridge.hookMethod(method, new ActivityHook());

        XposedHelpers.findAndHookMethod("android.widget.TextView",lpparam.classLoader,
                "onTextChanged", CharSequence.class, int.class, int.class, int.class,new EmojiEdit(ActivityHook.getCurrentActivity()));

    }

    public static void verboseLog(Object who, String methodAndParams, String what) {
        if (verbose) {
            String packageAndClass = (who instanceof Class) ? ((Class<?>) who).getCanonicalName() : who.getClass().getCanonicalName();
            String tag = "Hook (" + packageAndClass + "#" + methodAndParams + ")";

            Log.v(tag, what);
            XposedBridge.log(tag + " " + what);
        }
    }

    /**
     * Get the Context through the package we are hooking
     *
     * @param param An MethodHookParam contains the thisObject on which we can call methods
     * @return The context of our own package
     */
    public static Context getContext(XC_MethodHook.MethodHookParam param) {
        Context ret = null;

        try {
            // Get the current activity
            Context wifiSettingsContext = ((Activity) callMethod(param.thisObject, "getActivity"))
                    .getApplicationContext();

            // Create our own Context from the WifiSettings Context
            ret = wifiSettingsContext.createPackageContext
                    ("com.example.contextmenutest", Context.CONTEXT_IGNORE_SECURITY);

            // Thrown if the package could not be found
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return ret;
    }
}