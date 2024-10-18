package com.ingresso.challenge

import com.ingresso.challenge.model.Api
import com.ingresso.challenge.model.ApiResponseModel
import com.ingresso.challenge.model.ApiService
import com.ingresso.challenge.model.ImageModel
import com.ingresso.challenge.model.MovieModel
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.Before
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import retrofit2.Response
import java.io.IOException
import java.lang.RuntimeException

class ApiServiceTest {
    private lateinit var mockApi: Api
    private lateinit var apiService: ApiService

    @Before
    fun setUp() {
        mockApi = mock(Api::class.java)
        apiService = ApiService(mockApi)
    }

    // Caminho feliz: resposta esperada
    @Test
    fun testSuccessfulApiCall(): Unit = runBlocking {
        val mockMovie1 = MovieModel(
            id = "4242",
            title = "Inception",
            type = "Filme",
            images = listOf(
                ImageModel(
                    url = "inceptionPosterImg",
                    type = "PosterPortrait"
                )
            )
        )

        val mockMovie2 = MovieModel(
            id = "001",
            title = "Playtime",
            type = "Filme",
            images = listOf(
                ImageModel(
                    url = "playtimePosterImg",
                    type = "PosterPortrait"
                )
            )
        )

        val mockResponse = ApiResponseModel(listOf(mockMovie1, mockMovie2), 2)
        `when`(mockApi.getMovies()).thenReturn(Response.success(mockResponse))

        val result = apiService.getMovies()

        assertNotNull(result)
        assert(result.isNotEmpty())
        assertEquals(2, result.size)

        assertEquals("4242", result[0].id)
        assertEquals("Inception", result[0].title)
        assertEquals("Filme", result[0].type)
        assertEquals(1, result[0].images.size)
        assertEquals("inceptionPosterImg", result[0].images[0].url)
        assertEquals("PosterPortrait", result[0].images[0].type)

        assertEquals("001", result[1].id)
        assertEquals("Playtime", result[1].title)
        assertEquals("Filme", result[1].type)
        assertEquals(1, result[1].images.size)
        assertEquals("playtimePosterImg", result[1].images[0].url)
        assertEquals("PosterPortrait", result[1].images[0].type)

        assertEquals(2, result.count { it.id.isNotEmpty() })
    }

    // Explora erros no pedido da API. Códigos HTTP >= 300
    @Test
    fun testErrorResponseHandling() : Unit = runBlocking  {
        val errorResponse = Response.error<ApiResponseModel>(500, ResponseBody.create(null, "Internal Server Error"))
        `when`(mockApi.getMovies()).thenReturn(errorResponse)

        val result = apiService.getMovies()

        assertNull(result)
    }

    @Test
    fun testErrorResponseHandling2() : Unit = runBlocking  {
        `when`(mockApi.getMovies()).thenReturn(Response.success(null))

        val result = apiService.getMovies()

        assertNull(result)
    }

    // Edge case: volta resposta sem body
    @Test
    fun testEmptyResponseHandling() : Unit = runBlocking  {
        val errorResponse = Response.error<ApiResponseModel>(404, ResponseBody.create(null, ""))
        `when`(mockApi.getMovies()).thenReturn(errorResponse)

        val result = apiService.getMovies()

        assertNull(result)
    }

    // Edge case: cenário pouco provável sem filmes na lista
    @Test
    fun testEmptyMovieListResponseHandling(): Unit = runBlocking {
        val mockResponse = ApiResponseModel(emptyList(), 0)
        `when`(mockApi.getMovies()).thenReturn(Response.success(mockResponse))

        val result = apiService.getMovies()

        assertNotNull(result)
        assert(result.isEmpty())
    }


    // Explora erros na realização do pedido da API. Ficar sem conexão antes/durante
    @Test
    fun testNetworkFailureHandling():Unit = runBlocking {
        `when`(mockApi.getMovies()).thenThrow(RuntimeException("Network failure"))
        val result = apiService.getMovies()

        assertNull(result)
    }
}