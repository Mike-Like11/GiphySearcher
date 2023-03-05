package com.ivantsov.giphysearcher.data.model

import com.google.gson.annotations.SerializedName


data class Original(
    var height: String,
    var width: String,
    var size: String,
    var url: String,
    @SerializedName("mp4_size") var mp4Size: String,
    var mp4: String,
    @SerializedName("webp_size") var webpSize: String,
    var webp: String,
    var frames: String,
    var hash: String
)