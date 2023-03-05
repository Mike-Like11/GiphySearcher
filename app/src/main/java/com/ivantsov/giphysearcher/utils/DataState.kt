package com.ivantsov.giphysearcher.utils

sealed class DataState<out T> {
    class Loading<T> : DataState<T>()
    class Success<T>(val result: T) : DataState<T>()
    class Error<T>(val error: Throwable) : DataState<T>()
}