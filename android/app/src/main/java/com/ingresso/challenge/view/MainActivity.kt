package com.ingresso.challenge.view

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
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
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progress_bar)
        val retrofit = RetrofitClient.getClient()
        val api: Api = retrofit.create(Api::class.java)
        val apiService = ApiService(api)
        val repository = MovieRepository(apiService)

        // Inicializa nossa ViewModel com provider do Android
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(repository)
        )[MovieViewModel::class.java]

        // Acha a única RecyclerView na UI e configura seu layout manager
        // para permitir 3 itens por linha
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = GridLayoutManager(this, 3)

        // Começa a observar o LiveData "movies". Quando dados são atualizados,
        // atualiza-se o Adapter e este é setado na RecyclerView
        viewModel.movies.observe(this, { movies ->
            progressBar.visibility = View.GONE
            adapter = MovieAdapter(movies ?: emptyList())
            recyclerView.adapter = adapter
        })

        // Bate na API apenas uma vez para pegar o JSON com filmes
        progressBar.visibility = View.VISIBLE
        viewModel.fetchMovies()
    }
}
