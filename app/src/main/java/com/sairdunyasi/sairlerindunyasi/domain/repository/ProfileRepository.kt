package com.sairdunyasi.sairlerindunyasi.domain.repository

import com.sairdunyasi.sairlerindunyasi.domain.model.ProfileModel

interface ProfileRepository {
    suspend fun getProfile(): ProfileModel
    suspend fun updateProfile(newNick: String)
    suspend fun updateProfilePhoto(uri: String)
}
