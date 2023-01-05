package com.example.project

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.InputFilter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.project.client.ApiClient
import com.example.project.models.SendVerify
import com.example.project.models.Verify
import retrofit2.Call
import retrofit2.Response

class VerifyPassword : AppCompatActivity() {

    private var edtVerifyPass:EditText? = null
    private var txtVerifyTimer:TextView? = null
    private var btnVerify:Button? = null
    private var sharedPreferences: SharedPreferences? = null
    private var verified:String? = null
    private var token:String? = null
    private var email:String? = null
    private var user:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_verify_password)

        codeTimersText()

        edtVerifyPass = findViewById(R.id.edtVerifyPass)
        txtVerifyTimer = findViewById(R.id.txtVerifyTimer)
        btnVerify = findViewById(R.id.btnVerify)

        token = intent.getStringExtra("token").toString()
        verified = intent.getStringExtra("verified").toString()
        email = intent.getStringExtra("email").toString()

        edtVerifyPass?.filters = arrayOf(InputFilter.LengthFilter(6))

        btnVerify?.setOnClickListener {
            val code = edtVerifyPass?.text.toString()
            if (code.isEmpty()){
                edtVerifyPass?.error = "Enter password"
            }
            else if(code.length < 6){
                edtVerifyPass?.error = "The password is incomplete"
            }
            else{
                val verify = Verify(email,token)
                val verifyUser: Call<Verify> = ApiClient.userService.verifyUser(verify)
                verifyUser.enqueue(object : retrofit2.Callback<Verify> {
                    override fun onResponse(call: Call<Verify>, response: Response<Verify>) {
                        if (response.isSuccessful) {
                            val token = response.body()!!.token
                            sharedPreferences = getSharedPreferences("SharedPref", MODE_PRIVATE)
                            user = sharedPreferences?.edit()!!.putString("token", token).apply()
                                .toString()
                            val intent = Intent(this@VerifyPassword, PasscodePage::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                    override fun onFailure(call: Call<Verify>, t: Throwable) {
                        Toast.makeText(this@VerifyPassword,"Internet connection error", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }

    val sign = SendVerify(email, "")

    private fun codeTimersText() {
        object : CountDownTimer(59000, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                txtVerifyTimer?.text = "00:" + millisUntilFinished / 1000
            }

            @SuppressLint("SetTextI18n", "ResourceAsColor")
            override fun onFinish() {
                txtVerifyTimer?.text = "Resend code"
                txtVerifyTimer?.setTextColor(R.color.red)
                txtVerifyTimer?.setOnClickListener {
                    codeTimersText()
                    verified = ""
                    val resend:Call<SendVerify> = ApiClient.userService.resendVerification(sign)
                    resend.enqueue(object : retrofit2.Callback<SendVerify>{
                        override fun onResponse(call: Call<SendVerify>, response: Response<SendVerify>) {
                            if (response.isSuccessful){
                                val sendVerify = response.body()
                                if (sendVerify != null){
                                    verified = sendVerify.verifyCode
                                }
                            }
                        }

                        override fun onFailure(call: Call<SendVerify>, t: Throwable) {
                            Toast.makeText(this@VerifyPassword, "Error", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }
        }
    }
}














