package com.sairdunyasi.sairlerindunyasi.presentation.home.home_poems.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sairdunyasi.sairlerindunyasi.domain.model.PoemWithProfileModel
import com.sairdunyasi.sairlerindunyasi.domain.usecase.FetchPoemsUseCase
import com.sairdunyasi.sairlerindunyasi.domain.usecase.LikePoemUseCase
import com.sairdunyasi.sairlerindunyasi.presentation.home.home_poems.events.HomeEvents
import com.sairdunyasi.sairlerindunyasi.presentation.home.home_poems.state.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchPoemsUseCase: FetchPoemsUseCase,
    private val likePoemUseCase: LikePoemUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> get() = _state

    private var currentPoemIndex = 0

    init {
        fetchAllPoems()

    }

     fun fetchAllPoems() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val poems = fetchPoemsUseCase().firstOrNull() ?: emptyList()

                if (poems.isEmpty()) {
                } else {
                    poems.forEach { poem ->
                    }
                }

                updateState { copy(poems = poems, currentPoem = poems.firstOrNull(), isLoading = false) }
                currentPoemIndex = 0
            } catch (e: Exception) {
                updateState { copy(error = e.message, isLoading = false) }
            }
        }
    }


    private fun updateState(update: HomeState.() -> HomeState) {
        _state.value = _state.value.update()
    }

    fun onEvent(event: HomeEvents) {
        when (event) {
            is HomeEvents.OnFavoriteToggle -> toggleFavorite(event.poemId)
            HomeEvents.OnNextPoemClick -> navigateToNextPoem()
            HomeEvents.OnPreviousPoemClick -> navigateToPreviousPoem()
        }
    }

    private fun navigateToNextPoem() {
        val poems = _state.value.poems
        if (currentPoemIndex < poems.size - 1) {
            currentPoemIndex++
            updateState { copy(currentPoem = poems[currentPoemIndex]) }
        }
    }

    private fun navigateToPreviousPoem() {
        if (currentPoemIndex > 0) {
            currentPoemIndex--
            updateState { copy(currentPoem = _state.value.poems[currentPoemIndex]) }
        }
    }

    private fun toggleFavorite(poemId: String) {
        val currentPoem = _state.value.poems.find { it.id == poemId } ?: return

        val newFavoriteStatus = !currentPoem.isFavorite
        val newCounterFavNumber = if (newFavoriteStatus) {
            currentPoem.counterFavNumber + 1
        } else {
            (currentPoem.counterFavNumber - 1).coerceAtLeast(0)
        }

        val updatedPoem = currentPoem.copy(
            isFavorite = newFavoriteStatus,
            counterFavNumber = newCounterFavNumber
        )

        val updatedPoems = _state.value.poems.map { poem ->
            if (poem.id == poemId) updatedPoem else poem
        }

        updateState { copy(poems = updatedPoems, currentPoem = updatedPoem) }

        viewModelScope.launch {
            try {
                likePoemUseCase(poemId, newFavoriteStatus)
            } catch (e: Exception) {
                Log.e("FavoriteError", "Error toggling favorite", e)
            }
        }
    }


}
