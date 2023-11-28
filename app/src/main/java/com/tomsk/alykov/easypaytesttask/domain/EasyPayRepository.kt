package com.tomsk.alykov.easypaytesttask.domain

import com.tomsk.alykov.easypaytesttask.data.network.models.AuthResponseModel
import com.tomsk.alykov.easypaytesttask.data.network.models.PaymentsListResponseModel

interface EasyPayRepository {

    suspend fun login(login: String, password: String): AuthResponseModel?

    suspend fun getList(token: String): PaymentsListResponseModel?
}