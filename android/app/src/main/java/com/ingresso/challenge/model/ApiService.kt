package com.ingresso.challenge.model

import android.util.Log
import com.ingresso.challenge.RetrofitClient
import retrofit2.Response
import retrofit2.http.GET

interface Api {
    @GET("/events/coming-soon/partnership/desafio")
    suspend fun getMovies(): Response<ApiResponseModel>
}

class ApiService(private val api: Api) {
    suspend fun getMovies(): List<MovieModel>? {
        try {
            val response = api.getMovies()
            return response.body()?.items
        } catch (e: Exception) {
//            Log.e("ApiService.getMovies", e.toString())
            return null
        }
    }
}
