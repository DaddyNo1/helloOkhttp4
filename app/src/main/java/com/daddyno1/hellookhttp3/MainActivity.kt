package com.daddyno1.hellookhttp3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object{
        val TAG: String = "OKHTTP"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.e(TAG, classLoader.toString())
        Log.e(TAG, String::class.java.classLoader.toString())

        txt.setOnClickListener {
            startActivity(Intent(MainActivity@this, MainActivity2::class.java))
        }

        Log.e(TAG, "onCreate")
    }

    override fun onResume() {
        super.onResume()

        Log.e(TAG, "onResume")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.e(TAG, "onSaveInstanceState")
    }
}
