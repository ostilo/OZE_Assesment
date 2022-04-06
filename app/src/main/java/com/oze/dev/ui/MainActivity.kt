package com.oze.dev.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.gson.Gson
import com.oze.dev.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val navController by lazy{
        (supportFragmentManager.findFragmentById(R.id.nav_host_id) as NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigation()


    }

    private fun setupNavigation() {
        val bottomNavigationView =
            findViewById<BottomNavigationView>(R.id.nav_bottom_id)
        setupWithNavController(bottomNavigationView, navController)
    }

}