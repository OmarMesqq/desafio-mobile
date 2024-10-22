package com.ingresso.challenge.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ingresso.challenge.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        // carrega fragmento de filmes por padrão
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MoviesFragment())
                .commit()
        }

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_movies -> {
                    // aba principal com filmes
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, MoviesFragment())
                        .commit()
                    true
                }
                R.id.nav_about -> {
                    // aba "About"
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, AboutFragment())
                        .commit()
                    true
                }
                else -> false
            }
        }
    }
}
