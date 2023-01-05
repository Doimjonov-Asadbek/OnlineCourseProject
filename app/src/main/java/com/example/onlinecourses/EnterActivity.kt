package com.example.onlinecourses

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.onlinecourses.bottomBar.CoursesFragment
import com.example.onlinecourses.bottomBar.DashboardFragment
import com.example.onlinecourses.bottomBar.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class EnterActivity : AppCompatActivity() {

    private lateinit var bottomNavBar: BottomNavigationView
    private lateinit var frameLayout: FrameLayout

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_screen)

        val another = DashboardFragment()
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.frameLayout, another)
        transaction.commit()

        bottomNavBar = findViewById(R.id.bottomNavBar)
        frameLayout = findViewById(R.id.frameLayout)

        bottomNavBar.setOnItemSelectedListener {
            when(it.itemId){
                R.id.Dashboard -> loadFragment(DashboardFragment())
                R.id.Courses -> loadFragment(CoursesFragment())
                R.id.Settings -> loadFragment(SettingsFragment())
            }
            true
        }
    }

    private  fun loadFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, fragment)
        transaction.commit()
    }
}