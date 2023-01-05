package com.example.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class NewPassword : AppCompatActivity() {

    private lateinit var edtNewPass:EditText
    private lateinit var edtRepeatPass:EditText
    private lateinit var btnEnter:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_new_password)

        edtNewPass = findViewById(R.id.edtNewPass)
        edtRepeatPass = findViewById(R.id.edtRepeatPass)
        btnEnter = findViewById(R.id.btnEnter)

        btnEnter.setOnClickListener {
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
            finish()
        }
    }
}