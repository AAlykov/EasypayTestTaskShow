package com.tomsk.alykov.easypaytesttask.domain.usecases

import com.tomsk.alykov.easypaytesttask.data.network.models.PaymentsListResponseModel
import com.tomsk.alykov.easypaytesttask.domain.EasyPayRepository

class GetPaymentsListUseCase(private val easyPayRepository: EasyPayRepository) {

    suspend fun execute(token: String): PaymentsListResponseModel?
    {
        return easyPayRepository.getList(token)
    }
}