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
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import retrofit2.Response

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
        result?.isNotEmpty()?.let { assert(it) }
        assertEquals(2, result?.size)

        assertEquals("4242", result?.get(0)?.id)
        assertEquals("Inception", result?.get(0)?.title)
        assertEquals("Filme",result?.get(0)?.type)
        assertEquals(1, result?.get(0)?.images?.size)
        assertEquals("inceptionPosterImg", result?.get(0)?.images?.get(0)?.url)
        assertEquals("PosterPortrait",  result?.get(0)?.images?.get(0)?.type)

        assertEquals("001",  result?.get(1)?.id)
        assertEquals("Playtime",  result?.get(1)?.title)
        assertEquals("Filme",  result?.get(1)?.type)
        assertEquals(1,  result?.get(0)?.images?.size)
        assertEquals("playtimePosterImg",  result?.get(1)?.images?.get(0)?.url)
        assertEquals("PosterPortrait",  result?.get(1)?.images?.get(0)?.type)

        assertEquals(2, result?.count { it.id.isNotEmpty() })
    }

    // Explora erros na **lógica** do pedido da API. Códigos HTTP >= 300
    @Test
    fun testErrorResponseHandling() : Unit = runBlocking  {
        val mockResponse = Response.error<ApiResponseModel>(500, ResponseBody.create(null, "Internal Server Error"))
        `when`(mockApi.getMovies()).thenReturn(mockResponse)

        val result = apiService.getMovies()

        assertNull(result)
    }

    // Explora erros na **realização** do pedido da API. Ficar sem conexão antes/durante
    @Test
    fun testNetworkFailureHandling():Unit = runBlocking {
        `when`(mockApi.getMovies()).thenThrow(RuntimeException("Network failure"))
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
        result?.isEmpty()?.let { assert(it) }
    }

    // Edge case: resposta com body vazio. Se tivermos um 204 ou faha de parse, pode ser que volte null
    @Test
    fun testEmptyOrNonExistentResponseHandling() : Unit = runBlocking  {
        `when`(mockApi.getMovies()).thenReturn(Response.success(null))
        val result = apiService.getMovies()

        assertNull(result)
    }
}