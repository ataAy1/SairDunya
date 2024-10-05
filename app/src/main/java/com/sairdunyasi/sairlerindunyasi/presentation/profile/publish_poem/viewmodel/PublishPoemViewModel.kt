package com.sairdunyasi.sairlerindunyasi.presentation.profile.publish_poem.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sairdunyasi.sairlerindunyasi.domain.usecase.PublishPoemUseCase
import com.sairdunyasi.sairlerindunyasi.presentation.profile.publish_poem.event.PublishPoemEvent
import com.sairdunyasi.sairlerindunyasi.presentation.profile.publish_poem.event.PublishPoemState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PublishPoemViewModel @Inject constructor(
    private val publishPoemUseCase: PublishPoemUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PublishPoemState())
    val state: StateFlow<PublishPoemState> = _state

    fun onEvent(event: PublishPoemEvent) {
        when (event) {
            is PublishPoemEvent.PublishPoem -> publishPoem(event.title, event.content)
            PublishPoemEvent.Idle -> {}
        }
    }

    private fun publishPoem(title: String, content: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                publishPoemUseCase.addPoem(title, content)
                _state.value = _state.value.copy(isLoading = false, isSuccess = true)
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, errorMessage = "Hatalı İşlem!")
            }
        }
    }

}
