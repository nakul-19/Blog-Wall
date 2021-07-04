package com.nakul.blogWall.models.blog

data class BlogOwner(
    val created_at: String,
    val email: String,
    val id: Int,
    val last_login: String?,
    val profile_pic: String?,
    val username: String
)