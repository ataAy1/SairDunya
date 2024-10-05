package com.sairdunyasi.sairlerindunyasi.presentation.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sairdunyasi.sairlerindunyasi.domain.usecase.RegisterUseCase
import com.sairdunyasi.sairlerindunyasi.presentation.auth.register.event.RegisterEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _registerState = MutableStateFlow(RegisterState())
    val registerState: StateFlow<RegisterState> = _registerState

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.Submit -> {
                registerUser(event.email, event.nickname, event.password)
            }
        }
    }

    private fun registerUser(email: String, nickname: String, password: String) {
        viewModelScope.launch {
            _registerState.value = _registerState.value.copy(isLoading = true)

            val result = registerUseCase(email, nickname, password)

            result.onSuccess {
                _registerState.value = _registerState.value.copy(isLoading = false, isSuccess = true, error = null)
            }.onFailure {
                _registerState.value = _registerState.value.copy(isLoading = false, error = "Mail ve Kullanıcı Adınızı Kontrol Ediniz!", isSuccess = false)
            }
        }
    }
}
