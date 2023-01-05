package com.example.project

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.project.client.ApiClient
import com.example.project.models.Register
import retrofit2.Call
import retrofit2.Response

class RegistrationPage : AppCompatActivity() {

    private var edtRegisterEmail:EditText? = null
    private var edtRegisterPass:EditText? = null
    private var edtRegisterRepeatPass:EditText? = null
    private var btnSignUp:Button? = null
    private var txtSignIn:TextView? = null
    private var edtName:EditText? = null
    private var edtSurname:EditText? = null
    private var edtPhone:EditText? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_registration_page)
        edtRegisterEmail = findViewById(R.id.edtRegisterEmail)
        edtRegisterPass = findViewById(R.id.edtRegisterPass)
        edtRegisterRepeatPass = findViewById(R.id.edtRegisterRepeatPass)
        edtName = findViewById(R.id.edtName)
        edtSurname = findViewById(R.id.edtSurname)
        edtPhone = findViewById(R.id.edtPhone)
        btnSignUp = findViewById(R.id.btnSignUp)
        txtSignIn = findViewById(R.id.txtSignIn)

        txtSignIn?.setOnClickListener {
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
            finish()
        }

        btnSignUp?.setOnClickListener {
            if (edtRegisterEmail?.text.toString().isEmpty()){
                edtRegisterEmail?.error = "Enter your email"
            }
            else if(edtRegisterPass?.text.toString().isEmpty()){
                edtRegisterPass?.error = "Enter the password"
            }
            else if(edtRegisterRepeatPass?.text.toString().isEmpty()){
                edtRegisterRepeatPass?.error = "Enter the password"
            }
            else if(edtName?.text.toString().isEmpty()){
                edtName?.error = "Enter your name"
            }
            else if(edtSurname?.text.toString().isEmpty()){
                edtSurname?.error = "Enter your surname"
            }
            else if(edtPhone?.text.toString().isEmpty()){
                edtPhone?.error = "Enter your phone number"
            }
            else if (edtRegisterPass?.text.toString() != edtRegisterRepeatPass?.text.toString()){
                edtRegisterPass?.error = "Password must be the same"
                edtRegisterRepeatPass?.error = "Password must be the same"
            }
            else if (edtRegisterPass?.text.toString().length < 4){
                edtRegisterPass?.error = "Password must be at least 4 characters long"
            }
            else{
                val registration = Register(
                    edtRegisterEmail?.text.toString(),
                    edtRegisterPass?.text.toString(),
                    edtName?.text.toString(),
                    edtSurname?.text.toString(),
                    edtPhone?.text.toString(),
                    "Uzbekistan"
                )

                val signUp:Call<Register> = ApiClient.userService.register(registration)
                signUp.enqueue(object : retrofit2.Callback<Register>{
                    override fun onResponse(call: Call<Register>, response: Response<Register>) {
                        if (response.isSuccessful){
                            val user = response.body()
                            if (user != null){
                                Toast.makeText(this@RegistrationPage,"Success", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@RegistrationPage, VerifyPassword::class.java)
                                intent.putExtra("token", response.body()!!.token)
                                intent.putExtra("verified", response.body()!!.verified)
                                intent.putExtra("email", edtRegisterEmail?.text.toString())
                                startActivity(intent)
                            }
                        }
                    }

                    override fun onFailure(call: Call<Register>, t: Throwable) {
                        Toast.makeText(this@RegistrationPage,"Error", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }
}











