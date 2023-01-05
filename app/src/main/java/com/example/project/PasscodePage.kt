package com.example.project

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class PasscodePage : AppCompatActivity() {

    private var view1:View? = null
    private var view2:View? = null
    private var view3:View? = null
    private var view4:View? = null
    private var viewLog_one:View? = null
    private var viewLog_two:View? = null
    private var viewLog_three:View? = null
    private var viewLog_four:View? = null

    private var txtEnterPassword:TextView? = null
    private var button1:TextView? = null
    private var button2:TextView? = null
    private var button3:TextView? = null
    private var button4:TextView? = null
    private var button5:TextView? = null
    private var button6:TextView? = null
    private var button7:TextView? = null
    private var button8:TextView? = null
    private var button9:TextView? = null
    private var button0:TextView? = null

    private var imgFinger:ImageView? = null
    private var imgBack:ImageView? = null
    private var sharedPreferences: SharedPreferences? = null

    private var index = 0
    private var pasiIndex = false
    private var password = ""
    private var writePass = ""
    private var newPassword = ""
    private var click = true

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_passcode_page)

        txtEnterPassword = findViewById(R.id.txtEnterPassword)
        view1 = findViewById(R.id.view1)
        view2 = findViewById(R.id.view2)
        view3 = findViewById(R.id.view3)
        view4 = findViewById(R.id.view4)
        viewLog_one = findViewById(R.id.viewLog_one)
        viewLog_two = findViewById(R.id.viewLog_two)
        viewLog_three = findViewById(R.id.viewLog_three)
        viewLog_four = findViewById(R.id.viewLog_four)
        button1 = findViewById(R.id.txtPassOne)
        button2 = findViewById(R.id.txtPassTwo)
        button3 = findViewById(R.id.txtPassThree)
        button4 = findViewById(R.id.txtPassFour)
        button5 = findViewById(R.id.txtPassFive)
        button6 = findViewById(R.id.txtPassSix)
        button7 = findViewById(R.id.txtPassSeven)
        button8 = findViewById(R.id.txtPassEight)
        button9 = findViewById(R.id.txtPassNine)
        button0 = findViewById(R.id.txtPassZero)
        imgFinger = findViewById(R.id.imgFinger)
        imgBack = findViewById(R.id.imgBack)

        sharedPreferences = getSharedPreferences("Clone", Context.MODE_PRIVATE)
        password = sharedPreferences?.getString("password","").toString()

        button1?.setOnClickListener {
            if (click){
                index++
                writePass += "1"
                onClick()
            }
        }
        button2?.setOnClickListener {
            if (click){
                index++
                writePass += "2"
                onClick()
            }
        }
        button3?.setOnClickListener {
            if (click){
                index++
                writePass += "3"
                onClick()
            }
        }
        button4?.setOnClickListener {
            if (click){
                index++
                writePass += "4"
                onClick()
            }
        }
        button5?.setOnClickListener {
            if (click){
                index++
                writePass += "5"
                onClick()
            }
        }
        button6?.setOnClickListener {
            if (click){
                index++
                writePass += "6"
                onClick()
            }
        }
        button7?.setOnClickListener {
            if (click){
                index++
                writePass += "7"
                onClick()
            }
        }
        button8?.setOnClickListener {
            if (click){
                index++
                writePass += "8"
                onClick()
            }
        }
        button9?.setOnClickListener {
            if (click){
                index++
                writePass += "9"
                onClick()
            }
        }
        button0?.setOnClickListener {
            if (click){
                index++
                writePass += "0"
                onClick()
            }
        }
        imgBack?.setOnClickListener {
            if (click){
                index--
                backSpace()
            }
        }
    }

    private fun onClick() {
        if (index > 4){
            index = 4
        }
        when(index){
            1 -> {
                viewLog_one?.setBackgroundResource(R.drawable.click_password)
            }
            2 -> {
                viewLog_two?.setBackgroundResource(R.drawable.click_password)
            }
            3 -> {
                viewLog_three?.setBackgroundResource(R.drawable.click_password)
            }
            4 -> {
                viewLog_four?.setBackgroundResource(R.drawable.click_password)
                if (password == "" && pasiIndex){
                    if (newPassword == writePass) {
                        click = false
                        writePass = ""
                        index = 0
                        viewLog_one?.setBackgroundResource(R.drawable.success)
                        viewLog_two?.setBackgroundResource(R.drawable.success)
                        viewLog_three?.setBackgroundResource(R.drawable.success)
                        viewLog_four?.setBackgroundResource(R.drawable.success)
                        Handler(Looper.getMainLooper()).postDelayed({
                            click = true
                            sharedPreferences?.edit()?.putString("password", newPassword)?.apply()
                            startActivity(Intent(this, CategoryPage::class.java))
                            finish()
                        }, 500)
                    } else {
                        click = false
                        writePass = ""
                        index = 0
                        viewLog_one?.setBackgroundResource(R.drawable.error)
                        viewLog_two?.setBackgroundResource(R.drawable.error)
                        viewLog_three?.setBackgroundResource(R.drawable.error)
                        viewLog_four?.setBackgroundResource(R.drawable.error)
                        Handler(Looper.getMainLooper()).postDelayed({
                            click = true
                            viewLog_one?.setBackgroundResource(R.drawable.back_password)
                            viewLog_two?.setBackgroundResource(R.drawable.back_password)
                            viewLog_three?.setBackgroundResource(R.drawable.back_password)
                            viewLog_four?.setBackgroundResource(R.drawable.back_password)
                            txtEnterPassword?.text = getString(R.string.Repeat_password)
                            println(writePass)
                        }, 500)
                        Toast.makeText(this, "Password not match", Toast.LENGTH_SHORT).show()
                    }
                }
                if (password == "" && !pasiIndex) {
                    click = false
                    newPassword = writePass
                    writePass = ""
                    index = 0
                    pasiIndex = true
                    Handler(Looper.getMainLooper()).postDelayed({
                        click = true
                        viewLog_one?.setBackgroundResource(R.drawable.back_password)
                        viewLog_two?.setBackgroundResource(R.drawable.back_password)
                        viewLog_three?.setBackgroundResource(R.drawable.back_password)
                        viewLog_four?.setBackgroundResource(R.drawable.back_password)
                        txtEnterPassword?.text = getString(R.string.Repeat_password)
                    }, 500)
                }
                if (password != "" && !pasiIndex) {
                    if (writePass == password) {
                        click = false
                        writePass = ""
                        index = 0
                        viewLog_one?.setBackgroundResource(R.drawable.success)
                        viewLog_two?.setBackgroundResource(R.drawable.success)
                        viewLog_three?.setBackgroundResource(R.drawable.success)
                        viewLog_four?.setBackgroundResource(R.drawable.success)
                        Handler(Looper.getMainLooper()).postDelayed({
                            click = true
                            startActivity(Intent(this, CategoryPage::class.java))
                            finish()
                        }, 500)
                    } else {
                        click = false
                        writePass = ""
                        index = 0
                        viewLog_one?.setBackgroundResource(R.drawable.error)
                        viewLog_two?.setBackgroundResource(R.drawable.error)
                        viewLog_three?.setBackgroundResource(R.drawable.error)
                        viewLog_four?.setBackgroundResource(R.drawable.error)
                        Handler(Looper.getMainLooper()).postDelayed({
                            click = true
                            viewLog_one?.setBackgroundResource(R.drawable.back_password)
                            viewLog_two?.setBackgroundResource(R.drawable.back_password)
                            viewLog_three?.setBackgroundResource(R.drawable.back_password)
                            viewLog_four?.setBackgroundResource(R.drawable.back_password)
                        }, 500)
                        Toast.makeText(this, "Password not match", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun backSpace() {
        if (index in 0..3) {
            writePass = writePass.substring(0, writePass.length - 1)
        }
        if (index > 4) {
            index = 4
        }
        if (index < 0) {
            index = 0
        }
        when (index) {
            0 -> {
                viewLog_one?.setBackgroundResource(R.drawable.back_password)
            }
            1 -> {
                viewLog_two?.setBackgroundResource(R.drawable.back_password)
            }
            2 -> {
                viewLog_three?.setBackgroundResource(R.drawable.back_password)
            }
            3 -> {
                viewLog_four?.setBackgroundResource(R.drawable.back_password)
            }
        }
    }
}