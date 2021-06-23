package com.nakul.blogWall.network

import com.nakul.blogWall.models.auth.LoginModel
import com.nakul.blogWall.models.auth.LoginResponse
import com.nakul.blogWall.models.auth.RegisterModel
import com.nakul.blogWall.models.auth.RegisterResponse
import com.nakul.blogWall.utils.UtilConstants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthInterface {

    companion object {
        operator fun invoke(): AuthInterface {
            return RetrofitClient.getClient().create(AuthInterface::class.java)
        }
    }

    @POST(UtilConstants.register)
    suspend fun register(@Body m: RegisterModel): Response<RegisterResponse>

    @POST(UtilConstants.login)
    suspend fun login(@Body m: LoginModel): Response<LoginResponse>
}