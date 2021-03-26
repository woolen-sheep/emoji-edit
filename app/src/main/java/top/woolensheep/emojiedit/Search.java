package top.woolensheep.emojiedit;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.BreakIterator;
import java.util.ArrayList;

public class Search {

    static {
        System.loadLibrary("emoji_search");
    }

    public native static String jniSearch(String request, String path);

    public static ArrayList<String> SearchEmoji(String request, String path) {
        ArrayList<String> res = new ArrayList<String>();
        try {
            String json = jniSearch(request, path);
//            Log.i("emoji",json);
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                res.add(jsonObject.getJSONArray("emoji").get(0).toString());
//                String tmp = jsonObject.getJSONArray("name").get(0).toString();
//                BreakIterator bi = BreakIterator.getCharacterInstance();
//                bi.setText(tmp);
//                int b = bi.next();
//                res.add(tmp.substring(0, b));
            }
        } catch (JSONException e) {
            return res;
        }
        return res;
    }
}
