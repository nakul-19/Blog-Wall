package com.nakul.blogWall.models.blog

data class BlogResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: ArrayList<Blog>
)