package com.nakul.blogWall.models.user

import com.google.gson.annotations.SerializedName

data class User(
    val created_at: String,
    val email: String,
    val id: Int,
    val profile_pic: String,
    @SerializedName("username")
    val name: String
)