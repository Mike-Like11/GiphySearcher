package com.ivantsov.giphysearcher.data.model

import com.google.gson.annotations.SerializedName
import com.ivantsov.giphysearcher.data.model.*


data class Images(
    @SerializedName("original") var original: Original,
)