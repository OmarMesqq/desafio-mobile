package com.ingresso.challenge.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ingresso.challenge.R
import com.ingresso.challenge.viewmodel.MovieViewModel

class MainActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: MovieViewModel by viewModels()


        setContentView(R.layout.activity_main)
    }
}