package com.ingresso.challenge.model

class MovieRepository {
    suspend fun getMovies(): List<MovieModel>? {
        return ApiService.getMovies()
    }
}
