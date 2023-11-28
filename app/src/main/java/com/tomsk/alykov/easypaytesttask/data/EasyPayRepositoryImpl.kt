package com.tomsk.alykov.easypaytesttask.data

import android.util.Log
import com.tomsk.alykov.easypaytesttask.data.network.ApiFactory
import com.tomsk.alykov.easypaytesttask.data.network.models.AuthResponseModel
import com.tomsk.alykov.easypaytesttask.data.network.models.AuthRequestModel
import com.tomsk.alykov.easypaytesttask.data.network.models.PaymentsListResponseModel
import com.tomsk.alykov.easypaytesttask.domain.EasyPayRepository

class EasyPayRepositoryImpl(): EasyPayRepository {

    private val apiService = ApiFactory.easyPayApi

    override suspend fun login(login: String, password: String): AuthResponseModel? {
        val authRequest = AuthRequestModel(login, password)
        return apiService.auth(authRequest).body()
    }


    override suspend fun getList(token: String): PaymentsListResponseModel? {
        val paymentsList = apiService.getPaymentsList(token)
        return paymentsList.body()
    }
}