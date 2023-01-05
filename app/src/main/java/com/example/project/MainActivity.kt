package com.example.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        val sharedPreFence = getSharedPreferences("Clone", MODE_PRIVATE)
        val txtPass = sharedPreFence.getString("password","")

        if (txtPass == ""){
            Handler().postDelayed({
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
            finish()
            }, 500)
        }else{
            Handler().postDelayed({
            val intent = Intent(this, PasscodePage::class.java)
            startActivity(intent)
            finish()
        }, 500)
        }
    }
}