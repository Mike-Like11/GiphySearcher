package com.ivantsov.giphysearcher.data.model

import com.google.gson.annotations.SerializedName


data class User(
    @SerializedName("avatar_url") var avatarUrl: String?,
    @SerializedName("banner_image") var bannerImage: String? = null,
    @SerializedName("banner_url") var bannerUrl: String?,
    @SerializedName("profile_url") var profileUrl: String?,
    var username: String?,
    @SerializedName("display_name") var displayName: String?,
    var description: String?,
    @SerializedName("instagram_url") var instagramUrl: String?,
    @SerializedName("website_url") var websiteUrl: String?,
    @SerializedName("is_verified") var isVerified: Boolean
)