package com.example.don

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.don.R

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        Thread{
            Thread.sleep(2000)
            runOnUiThread {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }.start()
    }

    override fun onBackPressed() {
        this@WelcomeActivity.finish()
    }
}
