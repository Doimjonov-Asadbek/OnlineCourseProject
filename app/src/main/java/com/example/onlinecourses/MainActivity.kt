package com.example.onlinecourses

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.onlinecourses.fragments.LoginFragment

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var frame:FrameLayout
    private var sharedPreferences: SharedPreferences? = null
    private var password = ""

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        frame = findViewById(R.id.frame)
        sharedPreferences = getSharedPreferences("Clone", Context.MODE_PRIVATE)
        password = sharedPreferences?.getString("password","").toString()

        if (password == ""){
            Handler().postDelayed({
                val myFragment = LoginFragment()
                val fragment: Fragment? = supportFragmentManager.findFragmentByTag(LoginFragment::class.java.simpleName)

                if (fragment !is LoginFragment){
                    supportFragmentManager.beginTransaction()
                        .add(R.id.frame, myFragment, LoginFragment::class.java.simpleName)
                        .commit()
                }
            },1000)
        }else{
            Handler().postDelayed({
                val intent = Intent(this, EnterActivity::class.java)
                startActivity(intent)
                finish()
            },1000)
        }
    }
}