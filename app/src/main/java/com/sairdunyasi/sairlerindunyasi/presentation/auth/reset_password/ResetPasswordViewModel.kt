package com.sairdunyasi.sairlerindunyasi.presentation.auth.reset_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sairdunyasi.sairlerindunyasi.domain.usecase.ResetMailUseCase
import com.sairdunyasi.sairlerindunyasi.presentation.auth.reset_password.event.ResetEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val resetMailUseCase: ResetMailUseCase
) : ViewModel() {

    private val _resetState = MutableStateFlow(ResetState())
    val resetState: StateFlow<ResetState> get() = _resetState

    fun onEvent(event: ResetEvent) {
        when (event) {
            is ResetEvent.Submit -> resetPassword(event.email)
        }
    }

    private fun resetPassword(email: String) {
        viewModelScope.launch {
            _resetState.value = ResetState(isLoading = true)

            val result = resetMailUseCase.execute(email)
            if (result.isSuccess) {
                _resetState.value = ResetState(successMessage = "Şifre sıfırlama mail adresinize yollandı ..")
            } else {
                _resetState.value = ResetState(errorMessage ="Sıfırlama e-postası gönderilirken bir hata oluştu. Lütfen tekrar deneyin")
            }
        }
    }
}
