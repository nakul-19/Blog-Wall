package com.nakul.blogWall.models.blog

import com.google.gson.annotations.SerializedName

data class Blog(
    val content: String,
    val id: Int,
    val image: String?,
    val publish: String,
    val read_time: Int,
    val title: String,
    val topic: String,
    val url: String?,
    @SerializedName("user")
    val owner: BlogOwner
)