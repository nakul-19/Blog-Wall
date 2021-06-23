package com.nakul.blogWall.network

import com.nakul.blogWall.utils.UtilConstants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit.SECONDS

object RetrofitClient {
    private var retrofit: Retrofit? = null

    fun getClient(): Retrofit {
        val client = OkHttpClient.Builder().connectTimeout(0, SECONDS).readTimeout(0, SECONDS).writeTimeout(0,SECONDS)
        if (retrofit == null)
            retrofit = Retrofit.Builder()
                .baseUrl(UtilConstants.baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build()
        return retrofit!!
    }
}