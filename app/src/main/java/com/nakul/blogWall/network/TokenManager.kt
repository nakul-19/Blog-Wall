package com.nakul.blogWall.network

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.nakul.blogWall.models.network_event.Auth
import com.nakul.blogWall.repositories.AuthRepository
import com.nakul.blogWall.utils.UtilConstants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class TokenManager(var sp: SharedPreferences, api: AuthInterface) {

    companion object {
        private var instance: TokenManager? = null
        operator fun invoke(sp: SharedPreferences, api: AuthInterface): TokenManager {
            if (instance == null)
                instance = TokenManager(sp, api)
            return instance!!
        }
    }

    var accessToken: String
    private var refreshToken: String
    private var repository: AuthRepository = AuthRepository(api)

    val auth = MutableLiveData<Auth>()

    init {
        val res = repository.getLocalTokens(sp)
        accessToken = res.accessToken
        refreshToken = res.refreshToken
        if (refreshToken.isNotEmpty())
            auth.value = Auth.IsAuthenticated()
        else
            auth.value = Auth.Unauthenticated("Access Token Missing")

    }

    fun refresh(scope: CoroutineScope, call: () -> Unit) {
        Log.d("response refresh",accessToken)
        auth.postValue(Auth.Authenticating())

        scope.launch {
            try {

                val res = repository.refreshTokens(refreshToken)
                accessToken = res.token
                Log.d("response_refresh",accessToken)
                auth.postValue(Auth.IsAuthenticated())
                saveToken()
                call.invoke()

            } catch (e: Exception) {

                if (e is ApiException) {
                    auth.postValue(Auth.Unauthenticated(e.message.toString()))
                } else {
                    auth.postValue(Auth.NetworkError())
                }

            }
        }

    }

    private fun saveToken() {
        sp.edit().putString(UtilConstants.accessToken,accessToken).apply()
    }

}