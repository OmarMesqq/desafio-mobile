package com.ingresso.challenge.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ingresso.challenge.R
import com.ingresso.challenge.RetrofitClient
import com.ingresso.challenge.model.Api
import com.ingresso.challenge.model.ApiService
import com.ingresso.challenge.model.MovieRepository
import com.ingresso.challenge.viewmodel.MovieViewModel
import com.ingresso.challenge.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MovieViewModel
    private lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create the Retrofit client and API instance
        val retrofit = RetrofitClient.getClient()
        val api: Api = retrofit.create(Api::class.java)

        // Create ApiService and MovieRepository
        val apiService = ApiService(api)
        val repository = MovieRepository(apiService)

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(repository)
        )[MovieViewModel::class.java]

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        viewModel.movies.observe(this, { movies ->
            adapter = MovieAdapter(movies ?: emptyList())
            recyclerView.adapter = adapter
        })

        // Fetch movies
        viewModel.fetchMovies()
    }
}
