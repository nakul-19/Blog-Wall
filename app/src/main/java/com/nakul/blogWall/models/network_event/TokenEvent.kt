package com.nakul.blogWall.models.network_event

sealed class TokenEvent<T: Any> {
    class Success<T: Any>(val r: T, val msg: String): TokenEvent<T>()
    class Error<T : Any>(val msg: String): TokenEvent<T>()
    class Loading<T: Any>(): TokenEvent<T>()
    class AuthError<T: Any>(): TokenEvent<T>()
}