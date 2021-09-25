package com.nakul.blogWall.network

import com.nakul.blogWall.models.blog.BlogResponse
import com.nakul.blogWall.models.blog.NewBlogModel
import com.nakul.blogWall.models.blog.SingleBlogResponse
import com.nakul.blogWall.models.like_and_comment.CommentModel
import com.nakul.blogWall.models.like_and_comment.CommentingResponse
import com.nakul.blogWall.models.like_and_comment.LikeModel
import com.nakul.blogWall.utils.UtilConstants
import retrofit2.Response
import retrofit2.http.*

interface BlogInterface {

    companion object {
        operator fun invoke(): BlogInterface {
            return RetrofitClient.getClient().create(BlogInterface::class.java)
        }
    }

    @GET(UtilConstants.trending)
    suspend fun getTrendingBlogs(): Response<BlogResponse>

    @GET(UtilConstants.blogs)
    suspend fun getCategoryBlogs(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
        @Query("topic") topic: String
    ): Response<BlogResponse>

    @POST(UtilConstants.likeBlog)
    suspend fun likeBlog(
        @Header("Authorization") accessToken: String,
        @Query("obj_id") blogId: Int,
        @Body m: LikeModel
    ): Response<Any>

    @POST(UtilConstants.commentBlog)
    suspend fun commentBlog(
        @Header("Authorization") accessToken: String,
        @Query("obj_id") blogId: Int,
        @Body m: CommentModel
    ): Response<CommentingResponse>

    @GET(UtilConstants.blogs + "{id}")
    suspend fun getBlogById(@Path("id") id: Int): Response<SingleBlogResponse>

    @POST(UtilConstants.newBlog)
    suspend fun createBlog(
        @Header("Authorization") accessToken: String,
        @Body m: NewBlogModel
    ): Response<Any>

}