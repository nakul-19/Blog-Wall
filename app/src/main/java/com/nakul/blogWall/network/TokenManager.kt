package com.nakul.blogWall.network

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import com.nakul.blogWall.models.network_event.Auth
import com.nakul.blogWall.repositories.AuthRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.Response

class TokenManager(sp: SharedPreferences, api: AuthInterface) {

    companion object {
        private var instance: TokenManager? = null
        operator fun invoke(sp: SharedPreferences, api: AuthInterface): TokenManager {
            if (instance == null)
                instance = TokenManager(sp, api)
            return instance!!
        }
    }

    private var accessToken: String
    private var refreshToken: String
    private var repository: AuthRepository = AuthRepository(api)

    val auth = MutableLiveData<Auth>()

    init {
        val res = repository.getLocalTokens(sp)
        accessToken = res.accessToken
        refreshToken = res.refreshToken
        if (refreshToken.isNotEmpty())
            auth.value = Auth.isAuthenticated()
        else
            auth.value = Auth.unAuthenticated("Access Token Missing")

    }

    fun refresh(scope: CoroutineScope) {
        auth.postValue(Auth.authenticating())

        scope.launch {
            try {

                val res = repository.refreshTokens(refreshToken)
                accessToken = res.token
                auth.postValue(Auth.isAuthenticated())

            } catch (e: Exception) {

                if (e is ApiException) {
                    auth.postValue(Auth.unAuthenticated(e.message.toString()))
                } else {
                    auth.postValue(Auth.unAuthenticated("No Internet."))
                }

            }
        }

    }

}