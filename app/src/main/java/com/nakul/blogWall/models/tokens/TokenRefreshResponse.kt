package com.nakul.blogWall.models.tokens

import com.google.gson.annotations.SerializedName

data class TokenRefreshResponse(
    @SerializedName("access")
    val token: String
)