package com.ingresso.challenge.model

import retrofit2.HttpException
import retrofit2.Response
import retrofit2.http.GET

private const val apiEndpoint = "https://api-content.ingresso.com/v0/events/coming-soon/partnership/desafio"
interface Api {
    @GET(apiEndpoint)
    suspend fun getMovies(): Response<ApiResponseModel>
}

class ApiService(private val api: Api) {

    suspend fun getMovies(): List<MovieModel> {
        val response = api.getMovies()
        return if (response.isSuccessful) {
            response.body()?.items ?: emptyList()
        } else {
            emptyList()
        }
    }
}