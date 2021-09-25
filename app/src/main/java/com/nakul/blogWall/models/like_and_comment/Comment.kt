package com.nakul.blogWall.models.like_and_comment

import com.google.gson.annotations.SerializedName
import com.nakul.blogWall.models.user.User

data class Comment(
    val id: Int,
    val content_type: Int,
    val object_id: Int,
    val content: String,
    val timestamp: String,
    @SerializedName("user")
    val owner: User
)
