package com.tomsk.alykov.easypaytesttask.data.network.models

data class ErrorModel(
    val error_code: Int,
    val error_msg: String
)