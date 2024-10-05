package com.sairdunyasi.sairlerindunyasi.data.repository

import android.util.Log
import com.sairdunyasi.sairlerindunyasi.data.source.ProfileDataSource
import com.sairdunyasi.sairlerindunyasi.domain.model.ProfileModel
import com.sairdunyasi.sairlerindunyasi.domain.repository.ProfileRepository

class ProfileRepositoryImpl(
    private val profileDataSource: ProfileDataSource
) : ProfileRepository {

    override suspend fun getProfile(): ProfileModel {
        return try {
            val profileData = profileDataSource.getUserProfileData()
            profileData
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun updateProfile(newNick: String)  {
        try {
            profileDataSource.updateUserProfileData(newNick)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun updateProfilePhoto(uri: String) {
        profileDataSource.updateUserProfileDataWithUri(uri)
    }
}