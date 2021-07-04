package com.nakul.blogWall.models.network_event

sealed class Auth {
    class isAuthenticated : Auth()
    class authenticating : Auth()
    class unAuthenticated(val msg: String) : Auth()
}