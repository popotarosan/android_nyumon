package com.example.popotarou.dontpicktup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toggle.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val intent = Intent(this,AlarmService::class.java)
                startService(intent)
            }else{

                val intent = Intent(this,AlarmService::class.java)
                stopService(intent)
            }


        }
    }
}