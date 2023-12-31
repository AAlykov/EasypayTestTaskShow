package com.tomsk.alykov.easypaytesttask.data.network.models

import com.google.gson.annotations.SerializedName

data class AuthResponseModel(
    val success: Boolean,

    @SerializedName("response")
    val tokenResponse: TokenResponse? = null,

    @SerializedName("error")
    val errorModel: ErrorModel? = null
)