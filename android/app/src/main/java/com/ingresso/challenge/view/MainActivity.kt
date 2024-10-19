package com.ingresso.challenge.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ingresso.challenge.R
import com.ingresso.challenge.model.MovieRepository
import com.ingresso.challenge.viewmodel.MovieViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MovieViewModel
    private lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = MovieRepository()
        // Make sure to use the correct ViewModelProvider constructor
        viewModel = ViewModelProvider(
            this,
            com.ingresso.challenge.viewmodel.ViewModelFactory(repository)
        )[MovieViewModel::class.java]

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = GridLayoutManager(this, 2) // Adjust the number of columns

        viewModel.movies.observe(this, { movies ->
            adapter = MovieAdapter(movies ?: emptyList())
            recyclerView.adapter = adapter
        })

        // Fetch movies
        viewModel.fetchMovies()
    }
}
