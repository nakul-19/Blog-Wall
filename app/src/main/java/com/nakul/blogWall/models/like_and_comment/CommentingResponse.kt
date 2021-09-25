package com.nakul.blogWall.models.like_and_comment

data class CommentingResponse(
    val content: String,
    val id: Int,
    val timeStamp: String?
)
