package top.woolensheep.emojiedit;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EmojiClient {
    public EmojiClient() {
    }

    public List<String> GetEmojiList(String keyword) throws IOException {
        String url = "https://127.0.0.1:8083?request=" + keyword;
        Request request = new Request.Builder()
                .url(url)
                .build();
        OkHttpClient client = new OkHttpClient().newBuilder()
                .sslSocketFactory(getSSLSocketFactory(), (X509TrustManager) getTrustManager()[0])
                .hostnameVerifier(getHostnameVerifier())
                .build();
        String json;
        try (Response response = client.newCall(request).execute()) {
//            Log.i("response", response.body().string());
            json = response.body().string();
//            json="[\"123\",\"456\"]";
        }
        if (json == null || json.length() == 0) {
            List<String> res = new ArrayList<String>();
            res.add("aborted");
            return res;
        }
        return JSON.parseArray(json).toJavaList(String.class);
    }

    public Call GetEmojiListCall(String keyword) throws IOException {
        String url = "https://127.0.0.1:8083?request=" + keyword;
        Request request = new Request.Builder()
                .url(url)
                .build();
        OkHttpClient client = new OkHttpClient().newBuilder()
                .sslSocketFactory(getSSLSocketFactory(), (X509TrustManager) getTrustManager()[0])
                .hostnameVerifier(getHostnameVerifier())
                .build();
        String json;
        return client.newCall(request);
//        try (Response response = client.newCall(request).execute()) {
////            Log.i("response", response.body().string());
////            String json=response.body().string();
//            json="[\"123\",\"456\"]";
//        }
//        return JSON.parseArray(json).toJavaList(String.class);
    }

    public static SSLSocketFactory getSSLSocketFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, getTrustManager(), new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static TrustManager[] getTrustManager() {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[]{};
                    }
                }
        };
        return trustAllCerts;
    }

    public static HostnameVerifier getHostnameVerifier() {
        HostnameVerifier hostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        };
        return hostnameVerifier;
    }
}
