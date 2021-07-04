package com.nakul.blogWall.network

import com.nakul.blogWall.models.blog.BlogResponse
import com.nakul.blogWall.utils.UtilConstants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BlogInterface {

    companion object {
        operator fun invoke(): BlogInterface {
            return RetrofitClient.getClient().create(BlogInterface::class.java)
        }
    }

    @GET(UtilConstants.trending)
    suspend fun getTrendingBlogs(): Response<BlogResponse>

    @GET(UtilConstants.categoryBlogs)
    suspend fun getCategoryBlogs(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
        @Query("topic") topic: String
    ): Response<BlogResponse>

}