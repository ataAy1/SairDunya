package com.sairdunyasi.sairlerindunyasi.presentation.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sairdunyasi.sairlerindunyasi.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _loginState = MutableStateFlow(LoginState())
    val loginState: StateFlow<LoginState> = _loginState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = _loginState.value.copy(isLoading = true)
            val result = loginUseCase.invoke(email, password)
            result.fold(
                onSuccess = { _loginState.value = LoginState(isSuccess = true) },
                onFailure = { _loginState.value = LoginState(error = "Hatalı Giriş") }
            )
            _loginState.value = _loginState.value.copy(isLoading = false)
        }
    }
}
