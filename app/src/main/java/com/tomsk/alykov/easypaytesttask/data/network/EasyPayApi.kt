package com.tomsk.alykov.easypaytesttask.data.network

import com.tomsk.alykov.easypaytesttask.data.network.models.AuthResponseModel
import com.tomsk.alykov.easypaytesttask.data.network.models.AuthRequestModel
import com.tomsk.alykov.easypaytesttask.data.network.models.PaymentsListResponseModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST


interface EasyPayApi {

    @Headers(
        "Content-Type: application/json",
        "app-key: 12345",
        "v: 1"
    )
    @POST("api-test/login")
    suspend fun auth(@Body authRequest: AuthRequestModel): Response<AuthResponseModel>

    @Headers(
        "Content-Type: application/json",
        "app-key: 12345",
        "v: 1"
    )
    @GET("api-test/payments")
    suspend fun getPaymentsList(@Header("token") token: String): Response<PaymentsListResponseModel>

}
