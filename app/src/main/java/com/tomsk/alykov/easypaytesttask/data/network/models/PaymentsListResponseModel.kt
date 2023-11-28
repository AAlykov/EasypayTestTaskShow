package com.tomsk.alykov.easypaytesttask.data.network.models

import com.google.gson.annotations.SerializedName

data class PaymentsListResponseModel(
    @SerializedName("response")
    val paymentList: List<PaymentModel>,

    val success: Boolean,

    @SerializedName("error")
    val errorModel: ErrorModel?
)