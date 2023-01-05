package com.example.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class CategoryPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_category_page)
    }
}