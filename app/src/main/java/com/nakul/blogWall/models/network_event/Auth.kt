package com.nakul.blogWall.models.network_event

sealed class Auth {
    class IsAuthenticated : Auth()
    class Authenticating : Auth()
    class Unauthenticated(val msg: String) : Auth()
    class NetworkError(val msg: String = "No Internet.") : Auth()
}