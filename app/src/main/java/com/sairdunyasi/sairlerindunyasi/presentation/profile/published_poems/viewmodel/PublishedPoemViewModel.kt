package com.sairdunyasi.sairlerindunyasi.presentation.profile.published_poems.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sairdunyasi.sairlerindunyasi.domain.usecase.PublishedPoemsUseCase
import com.sairdunyasi.sairlerindunyasi.presentation.profile.published_poems.events.PublishedPoemEvent
import com.sairdunyasi.sairlerindunyasi.presentation.profile.published_poems.state.PublishedPoemState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PublishedPoemViewModel @Inject constructor(
    private val publishedPoemsUseCase: PublishedPoemsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<PublishedPoemState>(PublishedPoemState.Loading)
    val state: StateFlow<PublishedPoemState> = _state

    fun onEvent(event: PublishedPoemEvent) {
        when (event) {
            is PublishedPoemEvent.FetchPoems -> {
                viewModelScope.launch {
                    publishedPoemsUseCase.getUserPoems().collect { poems ->
                        _state.value = PublishedPoemState.Success(poems)
                    }
                }
            }
            is PublishedPoemEvent.DeletePoem -> {
                viewModelScope.launch {
                    try {
                        val result = publishedPoemsUseCase.deletePoem(event.poemId)
                        _state.value = PublishedPoemState.DeletionSuccess
                        onEvent(PublishedPoemEvent.FetchPoems)
                    } catch (e: Exception) {
                        _state.value = PublishedPoemState.Error("Error deleting poem: ${e.message}")
                    }
                }
            }

            is PublishedPoemEvent.DetailPoem -> {
            }
        }
    }
}
