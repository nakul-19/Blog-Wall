package com.nakul.blogWall.network

import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

abstract class SafeApiRequest {

    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {

        val response = call.invoke()

        if (response.isSuccessful && response.body() != null)
            return response.body()!!

        var msg = ""
        val error = response.errorBody()?.string()

        error?.let {
            try {
                msg += JSONObject(it).getString("detail")
            } catch (e: JSONException) {
                msg = response.message()
            }
        }

        throw ApiException(msg,response.code())
    }

    suspend fun <T : Any> requestNoResponse(call: suspend () -> Response<T>): Boolean {

        val response = call.invoke()
        if (response.code() / 100 == 2)
            return true

        var msg = ""
        val error = response.errorBody()?.string()

        error?.let {
            try {
                msg += JSONObject(it).getString("detail")
            } catch (e: JSONException) {
                msg = response.message()
            }
        }

        throw ApiException(msg,response.code())

    }

}

//Exception Class
class ApiException(e: String, val code: Int?=null) : Exception(e)