package com.nakul.blogWall.utils

object UtilConstants {

    const val fileName = "blogWallFile"
    const val name = "userName"
    const val loggedin = "loggedin"
    const val userId = "userId"
    const val userImage = "profilePic"
    const val accessToken = "blogWallAccessToken"
    const val refreshToken = "blogWallRefreshToken"

    const val baseurl = "https://blogwall.herokuapp.com"

    const val register = "/auth/register/"
    const val login = "/auth/login/"
    const val refresh = "/auth/token/refresh/"

    const val trending = "/api/posts/?ordering=likes&limit=6"
    const val blogs = "/api/posts/"
    const val likeBlog = "/api/likes/create/?type=post"
    const val commentBlog = "/api/comments/create/?type=post"

    const val newBlog = "/api/posts/create/"

    const val technology = "Technology"
    const val global = "Global Affairs"
    const val health = "Health"
    const val sports = "Sports"
    const val science = "Science"
    const val entertainment = "Entertainment"

}