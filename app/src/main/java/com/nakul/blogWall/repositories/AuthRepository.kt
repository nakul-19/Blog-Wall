package com.nakul.blogWall.repositories

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.nakul.blogWall.models.auth.LoginModel
import com.nakul.blogWall.models.auth.LoginResponse
import com.nakul.blogWall.models.auth.RegisterModel
import com.nakul.blogWall.models.auth.Token
import com.nakul.blogWall.models.tokens.TokenRefreshModel
import com.nakul.blogWall.models.tokens.TokenRefreshResponse
import com.nakul.blogWall.network.AuthInterface
import com.nakul.blogWall.network.SafeApiRequest
import com.nakul.blogWall.utils.UtilConstants

class AuthRepository(
    private val api: AuthInterface
) : SafeApiRequest() {

    suspend fun registerUser(user: RegisterModel) = apiRequest { api.register(user) }

    suspend fun loginUser(user: LoginModel) = apiRequest { api.login(user) }

    @SuppressLint("ApplySharedPref")
    fun saveUser(sp: SharedPreferences, res: LoginResponse) {
        sp.edit().putString(UtilConstants.name, res.name).commit()
        sp.edit().putString(UtilConstants.refreshToken, res.tokens.refreshToken).commit()
        sp.edit().putString(UtilConstants.accessToken, res.tokens.accessToken).commit()
        sp.edit().putBoolean(UtilConstants.loggedin, true).commit()
        sp.edit().putInt(UtilConstants.userId, res.id).commit()
        if (!res.profile_pic.isNullOrEmpty())
            sp.edit().putString(UtilConstants.userImage, UtilConstants.baseurl + res.profile_pic)
                .commit()
    }

    fun checkLogin(sp: SharedPreferences): Boolean {
        return sp.getBoolean(UtilConstants.loggedin, false)
    }

    fun getLocalTokens(sp: SharedPreferences): Token {
        val access = sp.getString(UtilConstants.accessToken,"")?:""
        val refresh = sp.getString(UtilConstants.refreshToken,"")?:""
        return Token(refresh,access)
    }

    suspend fun refreshTokens(token: String) = apiRequest { api.refreshToken(TokenRefreshModel(token)) }

}