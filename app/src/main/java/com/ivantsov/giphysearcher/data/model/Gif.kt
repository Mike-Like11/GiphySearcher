package com.ivantsov.giphysearcher.data.model

import com.google.gson.annotations.SerializedName


data class Gif (
  @SerializedName("id") var id: String,
  @SerializedName("username") var username: String? = null,
  @SerializedName("title") var title: String,
  @SerializedName("rating") var rating: String,
  @SerializedName("import_datetime") var importDatetime: String,
  @SerializedName("trending_datetime") var trendingDatetime: String,
  var images: Images,
  var user: User? = null
)