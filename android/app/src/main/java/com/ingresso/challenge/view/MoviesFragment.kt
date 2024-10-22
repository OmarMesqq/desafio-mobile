package com.ingresso.challenge.view

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
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

class MoviesFragment : Fragment(R.layout.fragment_movies) {
    private lateinit var viewModel: MovieViewModel
    private lateinit var adapter: MovieAdapter
    private lateinit var progressBar: ProgressBar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView and ProgressBar
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        progressBar = view.findViewById(R.id.progress_bar)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)

        // Setup ViewModel
        val retrofit = RetrofitClient.getClient()
        val api = retrofit.create(Api::class.java)
        val apiService = ApiService(api)
        val repository = MovieRepository(apiService)
        viewModel = ViewModelProvider(this, ViewModelFactory(repository))[MovieViewModel::class.java]

        viewModel.movies.observe(viewLifecycleOwner, { movies ->
            progressBar.visibility = View.GONE
            adapter = MovieAdapter(movies ?: emptyList())
            recyclerView.adapter = adapter
        })

        viewModel.fetchMovies()
        progressBar.visibility = View.VISIBLE
    }
}
