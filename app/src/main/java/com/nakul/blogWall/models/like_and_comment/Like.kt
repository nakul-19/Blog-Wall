package com.nakul.blogWall.models.like_and_comment

import com.nakul.blogWall.models.user.User

data class Like(
    val id: String,
    val like: Int,
    val user: User
)