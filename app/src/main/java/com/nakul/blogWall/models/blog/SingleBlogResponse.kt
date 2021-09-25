package com.nakul.blogWall.models.blog

import com.google.gson.annotations.SerializedName
import com.nakul.blogWall.models.like_and_comment.Comment
import com.nakul.blogWall.models.like_and_comment.Like
import com.nakul.blogWall.models.user.User

data class SingleBlogResponse(
    val comments: ArrayList<Comment>,
    val content: String,
    val id: Int,
    val image: String?,
    val likes: ArrayList<Like>,
    val publish: String,
    val read_time: Int,
    val title: String,
    val topic: String,
    val url: String?,
    @SerializedName("user")
    val owner: User
)
