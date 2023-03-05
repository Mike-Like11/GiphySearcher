package com.ivantsov.giphysearcher.screens.detailGif

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivantsov.giphysearcher.data.model.Gif
import com.ivantsov.giphysearcher.data.repository.GifRepository
import com.ivantsov.giphysearcher.data.repository.GifRepositoryImpl
import com.ivantsov.giphysearcher.utils.DataState
import kotlinx.coroutines.launch

class DetailGifViewModel(
    application: Application,
    private val gifRepository: GifRepository = GifRepositoryImpl(application.cacheDir)
) : ViewModel() {
    val gifDetail: MutableLiveData<DataState<Gif>> = MutableLiveData()
    fun getDetailedGif(id: String) = viewModelScope.launch {
        gifDetail.value = DataState.Loading()
        gifDetail.value = try {
            DataState.Success(gifRepository.getGifDetail(id))
        } catch (e: Exception) {
            DataState.Error(e)
        }
    }
}