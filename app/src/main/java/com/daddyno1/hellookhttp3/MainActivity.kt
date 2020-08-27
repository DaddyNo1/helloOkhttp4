package com.daddyno1.hellookhttp3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    companion object{
        val TAG: String = "OKHTTP"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.e(TAG, classLoader.toString())
        Log.e(TAG, String.javaClass.classLoader.toString())
    }
}
