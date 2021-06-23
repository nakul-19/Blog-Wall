package com.nakul.blogWall.models.auth

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("email")
    val email: String,
    @SerializedName("username")
    val name: String,
    @SerializedName("tokens")
    val tokens: Token
)

data class Token(
    @SerializedName("refresh")
    val refreshToken: String,
    @SerializedName("access")
    val accessToken: String
)
