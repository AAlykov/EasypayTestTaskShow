package com.tomsk.alykov.easypaytesttask.data.network.models

import com.google.gson.annotations.SerializedName

data class ErrorModel(
    @SerializedName("error_code")
    val errorCode: Int? = null,

    @SerializedName("error_msg")
    val errorMsg: String? =null
)