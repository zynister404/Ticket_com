package com.example.ticket_com

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.ticket_com.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup Manual Bottom Nav
        binding.bottomNavigation.menu.add(0, 1, 0, "Home").setIcon(android.R.drawable.ic_menu_compass)
        binding.bottomNavigation.menu.add(0, 2, 0, "Profile").setIcon(android.R.drawable.ic_menu_my_calendar)

        loadFragment(HomeFragment())

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                1 -> loadFragment(HomeFragment())
                2 -> loadFragment(ProfileFragment())
            }
            true
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}