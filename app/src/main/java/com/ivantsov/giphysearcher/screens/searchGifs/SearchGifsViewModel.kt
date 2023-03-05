package com.ivantsov.giphysearcher.screens.searchGifs

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivantsov.giphysearcher.data.model.Gif
import com.ivantsov.giphysearcher.data.repository.GifRepository
import com.ivantsov.giphysearcher.data.repository.GifRepositoryImpl
import com.ivantsov.giphysearcher.utils.DataState
import kotlinx.coroutines.launch

class SearchGifsViewModel(
    application: Application,
    private val gifRepository: GifRepository = GifRepositoryImpl(application.cacheDir)
) : ViewModel() {
    val searchedGifs: MutableLiveData<DataState<List<Gif>>> = MutableLiveData()
    private val searchTitle: MutableLiveData<String> = MutableLiveData("")
    fun setSearchTitle(query: String) {
        searchTitle.value = query
        getSearchedGifs()
    }

    fun getSearchedGifs() = viewModelScope.launch {
        searchedGifs.value = DataState.Loading()
        searchedGifs.value = try {
            if (searchTitle.value == "")
                DataState.Success(gifRepository.getTrendingGifs())
            else
                DataState.Success(gifRepository.getGifsByName(searchTitle.value.toString()))
        } catch (e: Exception) {
            DataState.Error(e)
        }
    }
}