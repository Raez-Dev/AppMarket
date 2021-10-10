package com.raezcorp.appmarketraez

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import androidx.core.view.GravityCompat
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.raezcorp.appmarketraez.databinding.ActivityMainMenuBinding

class MainMenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        val navController = Navigation.findNavController(this,R.id.start_nav_host_fragment)
        NavigationUI.setupWithNavController(binding.navigationView,navController)

        binding.imgMenu.setOnClickListener{
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }
}