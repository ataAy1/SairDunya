package com.sairdunyasi.sairlerindunyasi.presentation.profile.user_profile.events

import android.net.Uri

sealed class ProfileEvent {
    object LoadProfile : ProfileEvent()
    data class UpdateProfile(val userNick: String) : ProfileEvent()
    data class UpdateProfilePhoto(val uri: Uri) : ProfileEvent()
}
