package com.ingresso.challenge.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ingresso.challenge.model.MovieRepository

// Cria uma MovieViewModel recebendo o repositório via param para melhor coesão
class ViewModelFactory(private val repository: MovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // faz reflection para saber com segurança se a classe é compatível com MovieViewModel
        // isso garante que temos a ViewModel esperada e não tenhamos erros em runtime
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            return MovieViewModel(repository) as T
        }
        throw IllegalArgumentException("ViewModelFactory.classNotAssignableFrom.MovieViewModel")
    }
}
