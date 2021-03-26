package top.woolensheep.emojiedit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String path = this.getFilesDir().getAbsolutePath().toString();
        AssetsCopyer.releaseAssets(this.getApplicationContext(), "", path);
        if (!Utils.isRunService(this, "top.woolensheep.emojiedit.EmojiServer")) {
            Intent intent = new Intent(this, EmojiServer.class);
            startService(intent);
            Log.d("debug","Starting Service");
        }else{
            Log.d("debug","Service is already running");
        }
    }
}