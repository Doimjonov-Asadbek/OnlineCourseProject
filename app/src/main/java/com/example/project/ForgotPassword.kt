package com.example.project

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class ForgotPassword : AppCompatActivity() {

    private var edtForgotPhone:EditText? = null
    private var txtForgotTimer:TextView? = null
    private var btnForgotVerify:Button? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_forgot_password)

        edtForgotPhone = findViewById(R.id.edtForgotPhone)
        txtForgotTimer = findViewById(R.id.txtForgotTimer)
        btnForgotVerify = findViewById(R.id.btnForgotVerify)

        codeTimersText()

        btnForgotVerify?.setOnClickListener {
            val intent = Intent(this, NewPassword::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun codeTimersText() {
        object : CountDownTimer(59000, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                txtForgotTimer?.text = "00:" + millisUntilFinished / 1000
            }

            @SuppressLint("SetTextI18n", "ResourceAsColor")
            override fun onFinish() {
                txtForgotTimer?.text = "Resend code"
                txtForgotTimer?.setTextColor(R.color.red)
            }
        }
    }
}