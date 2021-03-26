package top.woolensheep.emojiedit;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.content.res.AssetManager;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import org.nanohttpd.protocols.http.NanoHTTPD;

import java.io.IOException;
import java.io.InputStream;

public class EmojiServer extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 创建对象，端口我这里设置为8080

        EmojiHTTP myServer = new EmojiHTTP(this.getFilesDir().getAbsolutePath() + "/index");
        try {

            AssetManager assets = getAssets();
            InputStream in = assets.open("keystore.bks");
            myServer.makeSecure(NanoHTTPD.makeSSLSocketFactory(in, "password".toCharArray()), null);
            // 开启HTTP服务
            myServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        return super.onStartCommand(intent, flags, startId);
        Intent activityIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplication(), 0, activityIntent, 0);
        String CHANNEL_ID = "top.woolensheep.emojiedit";
        String CHANNEL_NAME = "emoji";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        Notification notification = new NotificationCompat.Builder(getApplication(),CHANNEL_ID ).
                setContentText("Emoji Edit Running")
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
                .setPriority(Notification.PRIORITY_MAX).build();
        startForeground(1, notification);
        return START_STICKY;
    }
}