package com.nakul.blogWall.repositories

import com.nakul.blogWall.network.BlogInterface
import com.nakul.blogWall.network.SafeApiRequest

class BlogRepository(
    private val api: BlogInterface
) : SafeApiRequest() {

    companion object {
        const val pageSize = 1
    }

    suspend fun getTrendingBlogs() = apiRequest { api.getTrendingBlogs() }

    suspend fun getCategoriesBlogs(offset: Int, topic: String) = apiRequest {
        api.getCategoryBlogs(
            offset,
            pageSize, topic
        )
    }
}