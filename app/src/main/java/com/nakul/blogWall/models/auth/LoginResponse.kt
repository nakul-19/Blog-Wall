package com.nakul.blogWall.models.auth

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("email")
    val email: String,
    @SerializedName("username")
    val name: String,
    @SerializedName("tokens")
    val tokens: Token,
    @SerializedName("is_verified")
    val verified: Boolean,
    @SerializedName("profile_pic")
    var profile_pic: String?
)

data class Token(
    @SerializedName("refresh")
    val refreshToken: String,
    @SerializedName("access")
    val accessToken: String
)
