package com.ingresso.challenge.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ingresso.challenge.R

class BottomNavBarFragment : Fragment(R.layout.bottom_navigation_layout) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNavigationView: BottomNavigationView = view.findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_movies -> true
                R.id.nav_about -> {
                    activity?.let {
                        startActivity(Intent(it, AboutActivity::class.java))
                    }
                    true
                }

                else -> false
            }
        }
    }
}