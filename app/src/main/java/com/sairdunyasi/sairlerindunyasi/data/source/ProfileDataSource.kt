package com.sairdunyasi.sairlerindunyasi.data.source

import com.sairdunyasi.sairlerindunyasi.domain.model.ProfileModel

interface ProfileDataSource {
    suspend fun getUserProfileData(): ProfileModel
    suspend fun updateUserProfileData(newNick: String): Boolean
    suspend fun updateUserProfileDataWithUri(uri: String): Boolean
}
