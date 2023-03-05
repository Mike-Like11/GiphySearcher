package com.ivantsov.giphysearcher.data.remote

import com.ivantsov.giphysearcher.data.model.DetailGifResponse
import com.ivantsov.giphysearcher.data.model.SearchGifResponse
import com.ivantsov.giphysearcher.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("trending")
    suspend fun getTrendingGifs(
        @Query("api_key") api_key: String = Constants.API_KEY,
        @Query("limit") limit: Int = 25
    ): SearchGifResponse

    @GET("{id}")
    suspend fun getDetailedGif(
        @Path("id") id: String,
        @Query("api_key") api_key: String = Constants.API_KEY
    ): DetailGifResponse

    @GET("search")
    suspend fun getGifsByName(
        @Query("api_key") api_key: String = Constants.API_KEY,
        @Query("q") query: String,
        @Query("limit") limit: Int = 25
    ): SearchGifResponse
}