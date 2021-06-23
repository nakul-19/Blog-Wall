package com.nakul.blogWall.models.auth

import com.google.gson.annotations.SerializedName

data class RegisterModel(
    @SerializedName("email")
    val email: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String
)