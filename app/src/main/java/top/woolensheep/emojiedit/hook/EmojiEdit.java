package top.woolensheep.emojiedit.hook;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.alibaba.fastjson.JSON;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import top.woolensheep.emojiedit.EmojiClient;
import top.woolensheep.emojiedit.HookEdit;

import de.robv.android.xposed.XC_MethodHook;

public class EmojiEdit extends XC_MethodHook {
    public TextView that;

    private volatile Activity _currentActivity;

    public EmojiEdit(Activity activity) {
        Log.d("hook", "new emoji-edit created");
        _currentActivity = activity;
    }

    private boolean isTarget(Class<?> clazz) {
        try {
            Class classA = clazz.asSubclass(EditText.class);
            return true;
        } catch (ClassCastException e) {
            return false;
        }
    }


    @Override
    public void beforeHookedMethod(MethodHookParam param) throws Exception {

        while (ActivityHook.getCurrentActivity() == null) {
        }
        _currentActivity = ActivityHook.getCurrentActivity();
        if (!isTarget(param.thisObject.getClass())) return;
        CharSequence text = (CharSequence) param.args[0];
        int start = (int) param.args[1];
        int before = (int) param.args[2];
        int count = (int) param.args[3];
        if (before != 0) {
            _currentActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    that.startActionMode(new EmojiActionMode(new ArrayList<String>(), that, 0, 0), ActionMode.TYPE_FLOATING);
                }
            });
            return;
        }
        CharSequence update = text.subSequence(start, start + count);
        that = (TextView) param.thisObject;
        if (update.length() == 0) {
            _currentActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    that.startActionMode(new EmojiActionMode(new ArrayList<String>(), that, 0, 0), ActionMode.TYPE_FLOATING);
                }
            });
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                EmojiClient client = new EmojiClient();
                List<String> res = new ArrayList<String>();
                try {
                    res = client.GetEmojiList(update.toString());
                    if (res.size()>0&&res.get(0).equals("aborted")) return;
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ActionMode.Callback tmp = new EmojiActionMode(res, that, start, start + count);

                _currentActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        that.startActionMode(tmp, ActionMode.TYPE_FLOATING);
                    }
                });
            }
        }).start();
    }

    @Override
    public void afterHookedMethod(MethodHookParam param) {

    }

    private class EmojiActionMode extends ActionMode.Callback2 {
        private final List<String> finalRes;
        private final TextView that;
        private final int start;
        private final int end;

        public EmojiActionMode(List<String> res, TextView text, int s, int e) {
            finalRes = res;
            that = text;
            start = s;
            end = e;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
//            menu.add("<");
            finalRes.forEach(menu::add);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            String raw = that.getText().toString();
            if(item.getTitle().equals("<")){
                mode.finish();
                return false;
            }

            _currentActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    that.setText(raw.substring(0, start) + item.getTitle().toString() + raw.substring(end));
                    EditText edit = (EditText) that;
                    edit.setSelection(start + item.getTitle().toString().length());
                }
            });
            mode.finish();
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

        }
    }
}
