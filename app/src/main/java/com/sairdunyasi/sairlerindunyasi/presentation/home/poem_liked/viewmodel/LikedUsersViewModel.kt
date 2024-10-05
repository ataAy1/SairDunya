package com.sairdunyasi.sairlerindunyasi.presentation.home.poem_liked.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sairdunyasi.sairlerindunyasi.domain.usecase.FetchUsersWhoLikedPoemUseCase
import com.sairdunyasi.sairlerindunyasi.presentation.home.poem_liked.state.LikedUsersState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LikedUsersViewModel @Inject constructor(
    private val fetchUsersWhoLikedPoemUseCase: FetchUsersWhoLikedPoemUseCase
) : ViewModel() {

    private val _likedUsersState = MutableStateFlow(LikedUsersState())
    val likedUsersState: StateFlow<LikedUsersState> = _likedUsersState

    fun fetchLikedUsers(poemId: String) {
        viewModelScope.launch {
            _likedUsersState.value = _likedUsersState.value.copy(isLoading = true)
            try {
                val likedUsers = fetchUsersWhoLikedPoemUseCase(poemId)
                _likedUsersState.value = _likedUsersState.value.copy(users = likedUsers, isLoading = false)
            } catch (e: Exception) {
                _likedUsersState.value = _likedUsersState.value.copy(error = e.message, isLoading = false)
            }
        }
    }
}
