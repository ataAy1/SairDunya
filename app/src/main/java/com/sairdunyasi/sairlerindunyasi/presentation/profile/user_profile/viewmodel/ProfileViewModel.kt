package com.sairdunyasi.sairlerindunyasi.presentation.profile.user_profile.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sairdunyasi.sairlerindunyasi.data.utils.ImageUtils.uploadImageToStorage
import com.sairdunyasi.sairlerindunyasi.domain.usecase.ProfileUseCase
import com.sairdunyasi.sairlerindunyasi.presentation.profile.user_profile.events.ProfileEvent
import com.sairdunyasi.sairlerindunyasi.presentation.profile.user_profile.state.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase
) : ViewModel() {

    private val _profileState = MutableStateFlow<ProfileState>(ProfileState.Loading)
    val profileState: StateFlow<ProfileState> = _profileState

    private val _profileUpdatedEvent = MutableSharedFlow<Boolean>(replay = 0)
    val profileUpdatedEvent = _profileUpdatedEvent.asSharedFlow()

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.LoadProfile -> loadUserProfile()
            is ProfileEvent.UpdateProfile -> updateUserProfile(event.userNick)
            is ProfileEvent.UpdateProfilePhoto -> updateUserProfilePhoto(event.uri)
        }
    }

    private fun loadUserProfile() {
        viewModelScope.launch {
            _profileState.value = ProfileState.Loading
            runCatching {
                profileUseCase.getProfile()
            }.onSuccess { result ->
                result.onSuccess { profileData ->
                    _profileState.value = ProfileState.Success(profileData)
                }.onFailure { throwable ->
                    _profileState.value = ProfileState.Error(
                        throwable.localizedMessage ?: "Hatalı İşlem"
                    )
                }
            }.onFailure { throwable ->
                _profileState.value = ProfileState.Error(
                    throwable.localizedMessage ?: "Hatalı İşlem"
                )
            }
        }
    }


    private fun updateUserProfile(userNick: String) {
        viewModelScope.launch {
            updateProfileState(ProfileState.Updating)
            runCatching {
                profileUseCase.updateProfile(userNick)
            }.onSuccess {
                updateProfileState(ProfileState.UpdateSuccess("Kullanıcı Adı Güncellendi."))
            }.onFailure { throwable ->
                handleError(throwable)
            }
        }
    }

    private fun updateUserProfilePhoto(uri: Uri) {
        viewModelScope.launch {
            updateProfileState(ProfileState.Updating)
            runCatching {
                val downloadUrl = uploadImageToStorage(uri)
                profileUseCase.updateProfilePhoto(downloadUrl)
            }.onSuccess {
                updateProfileState(ProfileState.UpdateSuccess("Profil Fotoğrafı Güncellendi."))
            }.onFailure { throwable ->
                handleError(throwable)
            }
        }
    }

    private fun updateProfileState(newState: ProfileState) {
        if (_profileState.value != newState) {
            _profileState.value = newState
        }
    }

    private fun handleError(throwable: Throwable) {
        Timber.e(throwable)
        updateProfileState(ProfileState.Error(throwable.localizedMessage ?: "Hatalı İşlem"))
    }
}
