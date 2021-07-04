package com.nakul.blogWall.models.tokens

import com.google.gson.annotations.SerializedName

data class TokenRefreshModel(
    @SerializedName("refresh")
    val token: String
)