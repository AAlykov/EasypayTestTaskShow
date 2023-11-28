package com.tomsk.alykov.easypaytesttask.domain.usecases

import com.tomsk.alykov.easypaytesttask.data.network.models.AuthResponseModel
import com.tomsk.alykov.easypaytesttask.domain.EasyPayRepository

class LoginUseCase (private val easyPayRepository: EasyPayRepository) {

    suspend fun execute(login: String, password: String): AuthResponseModel?
    {
        return easyPayRepository.login(login, password)
    }
}