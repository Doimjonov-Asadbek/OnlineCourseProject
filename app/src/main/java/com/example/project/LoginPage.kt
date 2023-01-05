package com.example.project

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.project.client.ApiClient
import com.example.project.models.SignIn
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Response

@Suppress("DEPRECATION")
class LoginPage : AppCompatActivity() {

    private var edtLoginEmail:EditText? = null
    private var edtLoginPass:EditText? = null
    private var txtForgotPass:TextView? = null
    private var btnSignIn:Button? = null
    private var txtSignUp:TextView? = null
    var json: JsonObject? = null
    val gson = Gson()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_login_page)

        edtLoginEmail = findViewById(R.id.edtLoginEmail)
        edtLoginPass = findViewById(R.id.edtLoginPass)
        txtForgotPass = findViewById(R.id.txtForgotPass)
        btnSignIn = findViewById(R.id.btnSignIn)
        txtSignUp = findViewById(R.id.txtSignUp)

        val sharedPreFence = getSharedPreferences("Clone", MODE_PRIVATE)
        val txtPass = sharedPreFence.getString("password","")

        txtForgotPass?.setOnClickListener {
            val intent = Intent(this, ForgotPassword::class.java)
            startActivity(intent)
            finish()
        }

        txtSignUp?.setOnClickListener {
            val intent = Intent(this, RegistrationPage::class.java)
            startActivity(intent)
            finish()
        }

        btnSignIn?.setOnClickListener {
            if (edtLoginEmail?.text.toString().isEmpty()){
                edtLoginEmail?.error = "Enter email"
            }
            else if (edtLoginPass?.text.toString().isEmpty()){
                edtLoginPass?.error = "Enter password"
            }
            else if (edtLoginPass?.text.toString().length < 4){
                edtLoginPass?.error = "Password must be at least 4 characters long"
            }
            else {

                val signIn = SignIn(
                    edtLoginEmail?.text.toString(),
                    edtLoginPass?.text.toString()
                )

                val login: retrofit2.Call<SignIn> = ApiClient.userService.signIn(signIn)
                login.enqueue(object : retrofit2.Callback<SignIn> {
                    override fun onResponse(call: retrofit2.Call<SignIn>, response: Response<SignIn>) {
                        val code = response.code()
                        if (code == 200){
                            val user = response.body()
                            if (user != null){
                                if (txtPass == ""){
                                    Handler().postDelayed({
                                        val intent = Intent(this@LoginPage, PasscodePage::class.java)
                                        startActivity(intent)
                                        finish()
                                    }, 500)
                                } else{
                                    Handler().postDelayed({
                                        val intent = Intent(this@LoginPage, CategoryPage::class.java)
                                        startActivity(intent)
                                        finish()
                                        }, 500)
                                    }
                            }
                        }

                        if (code == 403){
                            json = gson.fromJson(response.errorBody()?.charStream(), JsonObject::class.java)
                            val error = json?.get("message")?.asString

                            if (error == "User not verified"){
                                AlertDialog.Builder(this@LoginPage)
                                    .setTitle("User not verified")
                                    .setPositiveButton("OK"){ dialog, _ ->
                                        val intent = Intent(this@LoginPage,VerifyPassword::class.java)
                                        startActivity(intent)
                                        finish()
                                    }
                                    .show()
                            }

                            if (error == "User blocked"){
                                AlertDialog.Builder(this@LoginPage)
                                    .setTitle("User is blocked !")
                                    .setMessage("Your account has been blocked. Please contact the administrator!")
                                    .setPositiveButton("OK"){ dialog, _ ->
                                        dialog.dismiss()
                                    }
                                    .show()
                            }
                        }

                        if (code == 401){
                            json = gson.fromJson(response.errorBody()?.charStream(), JsonObject::class.java)
                            val error = json?.get("message")?.asString

                            if (error == "Invalid credentials") {
                                edtLoginPass?.error = "Password incorrect"
                            }
                        }

                        if (code == 404){
                            AlertDialog.Builder(this@LoginPage)
                                .setTitle("User not found !")
                                .setMessage("This user was not found")
                                .setPositiveButton("OK"){ dialog, _ ->
                                    dialog.dismiss()
                                }
                                .show()
                        }
                    }

                    override fun onFailure(call: retrofit2.Call<SignIn>, t: Throwable) {
                        Toast.makeText(this@LoginPage, "Error", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }
}