package com.ingresso.challenge.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ingresso.challenge.R
import com.ingresso.challenge.RetrofitClient
import com.ingresso.challenge.model.Api
import com.ingresso.challenge.model.ApiService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val retrofit = RetrofitClient.getClient()
        val api = retrofit.create(Api::class.java)
        val apiService = ApiService(api)

        lifecycleScope.launch {
            val res = apiService.getMovies()
            println("apiResponse is: ${res}")
        }

        setContentView(R.layout.activity_main)
    }
}