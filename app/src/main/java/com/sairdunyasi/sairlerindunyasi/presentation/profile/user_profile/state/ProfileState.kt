package com.sairdunyasi.sairlerindunyasi.presentation.profile.user_profile.state

import com.sairdunyasi.sairlerindunyasi.domain.model.ProfileModel

sealed class ProfileState {
    object Loading : ProfileState()
    data class Success(val profileData: ProfileModel) : ProfileState()
    data class UpdateSuccess(val message: String) : ProfileState()
    data class Error(val errorMessage: String) : ProfileState()
    object Updating : ProfileState()
}
