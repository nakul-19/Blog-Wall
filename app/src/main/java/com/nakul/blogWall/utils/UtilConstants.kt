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

    const val trending = "/api/posts/?limit=6"
    const val categoryBlogs = "/api/posts/"

}