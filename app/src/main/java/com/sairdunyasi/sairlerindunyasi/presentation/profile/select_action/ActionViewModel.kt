package com.sairdunyasi.sairlerindunyasi.presentation.profile.select_action

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sairdunyasi.sairlerindunyasi.domain.usecase.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActionViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
        }
    }
}