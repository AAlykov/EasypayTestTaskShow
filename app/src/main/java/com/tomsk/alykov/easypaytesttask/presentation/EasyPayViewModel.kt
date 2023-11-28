package com.tomsk.alykov.easypaytesttask.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomsk.alykov.easypaytesttask.data.EasyPayRepositoryImpl
import com.tomsk.alykov.easypaytesttask.data.network.models.PaymentModel
import com.tomsk.alykov.easypaytesttask.domain.usecases.GetPaymentsListUseCase
import com.tomsk.alykov.easypaytesttask.domain.usecases.LoginUseCase
import kotlinx.coroutines.launch

class EasyPayViewModel : ViewModel() {

    private val easyPayRepositoryImpl = EasyPayRepositoryImpl()
    private val loginUseCase = LoginUseCase(easyPayRepositoryImpl)
    private val getPaymentsListUseCase = GetPaymentsListUseCase(easyPayRepositoryImpl)

    private val _isLoad = MutableLiveData<Boolean>()
    val isLoad: LiveData<Boolean> = _isLoad

    private val _token = MutableLiveData<String?>()
    val token: LiveData<String?> = _token

    private val _errorResponse = MutableLiveData<String?>()
    val errorResponse: LiveData<String?> = _errorResponse

    private val _isLogin = MutableLiveData<Boolean>()
    val isLogin: LiveData<Boolean> = _isLogin

    private val _paymentsList = MutableLiveData<List<PaymentModel>?>()
    val paymentsList: LiveData<List<PaymentModel>?> = _paymentsList

    private val _errorPaymentsList = MutableLiveData<String>()
    val errorPaymentsList: LiveData<String> = _errorPaymentsList


    fun login(login: String, password: String) {
        _isLoad.value = true
        viewModelScope.launch {
            val authResponse = loginUseCase.execute(login, password)

            if (authResponse != null) {
                if (authResponse.success) {
                    val token = authResponse.tokenResponse?.token
                    _token.postValue(token)
                    _isLogin.postValue(true)
                } else {
                    _isLogin.postValue(false)
                    val errorString =
                        "${authResponse.errorModel?.error_code}. ${authResponse.errorModel?.error_msg}"
                    _errorResponse.postValue(errorString)
                }
            } else {
                _errorResponse.postValue("Unknown error")
            }
            _isLoad.postValue(false)
        }
    }

    fun logout() {
        _token.value = null
    }

    fun getPaymentsList(token: String) {
        viewModelScope.launch {
            val paymentsListResponse = getPaymentsListUseCase.execute(token)
            if (paymentsListResponse != null && paymentsListResponse.success) {
                _paymentsList.postValue(paymentsListResponse.paymentList)
            } else {
                _paymentsList.postValue(null)
                val errorString =
                    "${paymentsListResponse?.errorModel?.error_code}. ${paymentsListResponse?.errorModel?.error_msg}"
                _errorPaymentsList.postValue(errorString)
            }
        }
    }

}