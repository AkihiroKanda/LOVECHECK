package com.example.smura.lovecheck_v2;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    Handler redrawHandler;
    String text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        redrawHandler = new Handler();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        String requesturl = "http://192.168.1.93:3000/";
        Request request = new Request.Builder().url(requesturl).get().build();
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // nothing
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                text = response.body().string();
                redrawHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        ((TextView)findViewById(R.id.MainText)).setText(text);
                    }
                });
            }
        });
        return super.onTouchEvent(event);
    }
}