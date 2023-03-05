package com.ivantsov.giphysearcher.data.repository

import com.ivantsov.giphysearcher.data.model.Gif

interface GifRepository {
    suspend fun getTrendingGifs(): List<Gif>
    suspend fun getGifDetail(id: String): Gif
    suspend fun getGifsByName(query: String): List<Gif>
}