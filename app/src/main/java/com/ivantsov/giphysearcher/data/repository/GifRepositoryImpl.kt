package com.ivantsov.giphysearcher.data.repository

import com.ivantsov.giphysearcher.data.model.Gif
import com.ivantsov.giphysearcher.data.remote.ApiService
import com.ivantsov.giphysearcher.data.remote.RetrofitServiceBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class GifRepositoryImpl(
    cacheDirectory: File,
    private val apiService: ApiService = RetrofitServiceBuilder.getService(
        cacheDirectory
    )
) : GifRepository {
    override suspend fun getTrendingGifs(): List<Gif> = withContext(Dispatchers.IO) {
        apiService.getTrendingGifs().data
    }

    override suspend fun getGifDetail(id: String): Gif = withContext(Dispatchers.IO) {
        apiService.getDetailedGif(id).data
    }

    override suspend fun getGifsByName(query: String) = withContext(Dispatchers.IO) {
        apiService.getGifsByName(query = query).data
    }
}