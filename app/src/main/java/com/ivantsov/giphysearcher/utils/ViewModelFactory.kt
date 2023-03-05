package com.ivantsov.giphysearcher.utils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ivantsov.giphysearcher.screens.detailGif.DetailGifViewModel
import com.ivantsov.giphysearcher.screens.searchGifs.SearchGifsViewModel

class ViewModelFactory(
    private val app: Application,
) : ViewModelProvider.AndroidViewModelFactory(app) {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(
                SearchGifsViewModel::class.java
            )
        ) {
            return SearchGifsViewModel(
                app
            ) as T
        } else {
            if (modelClass.isAssignableFrom(
                    DetailGifViewModel::class.java
                )
            ) {
                return DetailGifViewModel(
                    app
                ) as T
            }
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}