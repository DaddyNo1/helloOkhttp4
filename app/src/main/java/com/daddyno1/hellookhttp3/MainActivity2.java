package com.daddyno1.hellookhttp3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity2 extends AppCompatActivity {

    private static final String TAG = "OKHTTP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Log.e(TAG, "MainActivity2 - onCreate");
    }
}