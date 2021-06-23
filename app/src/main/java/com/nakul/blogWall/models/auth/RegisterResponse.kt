package com.nakul.blogWall.models.auth

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("email")
    val email: String,
    @SerializedName("is_tk_send")
    val sent: Boolean,
    @SerializedName("username")
    val name: String
)