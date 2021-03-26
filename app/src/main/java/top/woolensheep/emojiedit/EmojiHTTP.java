package top.woolensheep.emojiedit;

import android.util.Log;

import java.util.ArrayList;

import com.alibaba.fastjson.JSONArray;

import java.util.Map;
import java.util.logging.Logger;

import org.nanohttpd.protocols.http.IHTTPSession;
import org.nanohttpd.protocols.http.NanoHTTPD;
import org.nanohttpd.protocols.http.request.Method;
import org.nanohttpd.protocols.http.response.IStatus;
import org.nanohttpd.protocols.http.response.Response;
import org.nanohttpd.protocols.http.response.Status;
import org.nanohttpd.util.ServerRunner;

/**
 * An example of subclassing NanoHTTPD to make a custom HTTP server.
 */
public class EmojiHTTP extends NanoHTTPD {
    private boolean mutex;
    private long time;
    private String path;
    /**
     * logger to log to.
     */
    private static final Logger LOG = Logger.getLogger(EmojiHTTP.class.getName());

    public static void main(String[] args) {
        ServerRunner.run(EmojiHTTP.class);
    }

    public EmojiHTTP(String p) {
        super(8083);
        this.path = p;
        mutex = false;
        time = System.currentTimeMillis();
    }

    @Override
    public Response serve(IHTTPSession session) {
        if (System.currentTimeMillis() - time < 100)
            return Response.newFixedLengthResponse(Status.BAD_REQUEST, "", "");
        time=System.currentTimeMillis();
        while (mutex) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        mutex=true;
        Method method = session.getMethod();
        String uri = session.getUri();
        EmojiHTTP.LOG.info(method + " '" + uri + "' ");

        Map<String, String> parms = session.getParms();
        String req = parms.get("request");
        Log.d("debug",req);
        String msg = "";
        if (req != null) {
            ArrayList<String> res = Search.SearchEmoji(req, path);
            msg = JSONArray.toJSONString(res);
        }

        mutex=false;
        return Response.newFixedLengthResponse(msg);
    }
}