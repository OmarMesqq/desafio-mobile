package com.ingresso.challenge.model

class MovieRepository(private val apiService: ApiService) {
    suspend fun getMovies(): List<MovieModel>? {
        return apiService.getMovies()?.sortedBy { it.premiereDate?.localDate }
    }
}
