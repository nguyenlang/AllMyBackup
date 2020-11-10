package com.example.clientfarm

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnRun.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://www.youtube.com/watch?v=VWLGzTS2goo")
            intent.setPackage("com.google.android.youtube")
            startActivity(intent)
        }

        btnStop.setOnClickListener{
            var activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            var startMain = Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);

            //activityManager.restartPackage("com.google.android.youtube")
            activityManager.killBackgroundProcesses("com.google.android.youtube");
        }

    }

}