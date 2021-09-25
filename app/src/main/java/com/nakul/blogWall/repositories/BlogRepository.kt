package com.nakul.blogWall.repositories

import com.nakul.blogWall.models.blog.NewBlogModel
import com.nakul.blogWall.models.like_and_comment.CommentModel
import com.nakul.blogWall.models.like_and_comment.LikeModel
import com.nakul.blogWall.network.BlogInterface
import com.nakul.blogWall.network.SafeApiRequest

class BlogRepository(
    private val api: BlogInterface
) : SafeApiRequest() {

    companion object {
        const val pageSize = 5
    }

    suspend fun getTrendingBlogs() = apiRequest { api.getTrendingBlogs() }

    suspend fun getCategoriesBlogs(offset: Int, topic: String) = apiRequest {
        api.getCategoryBlogs(
            offset,
            pageSize,
            topic
        )
    }

    suspend fun likeBlog(token: String, id: Int, likes: LikeModel) =
        requestNoResponse { api.likeBlog(token, id, likes) }

    suspend fun commentBlog(token: String, id: Int, content: String) =
        apiRequest { api.commentBlog(token, id, CommentModel((content))) }

    suspend fun getBlogById(id: Int) = apiRequest { api.getBlogById(id) }

    suspend fun newBlog(token: String, m: NewBlogModel) = apiRequest { api.createBlog(token, m) }
}