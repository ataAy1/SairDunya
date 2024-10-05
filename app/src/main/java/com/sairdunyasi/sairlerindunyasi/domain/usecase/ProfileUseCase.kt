package com.sairdunyasi.sairlerindunyasi.domain.usecase

import com.sairdunyasi.sairlerindunyasi.domain.model.ProfileModel
import com.sairdunyasi.sairlerindunyasi.domain.repository.ProfileRepository

import javax.inject.Inject

class ProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend fun getProfile(): Result<ProfileModel> {
        return runCatching { profileRepository.getProfile() }
    }

    suspend fun updateProfile(newNick: String): Result<Unit> {
        return runCatching { profileRepository.updateProfile(newNick) }
    }

    suspend fun updateProfilePhoto(uri: String): Result<Unit> {
        return runCatching { profileRepository.updateProfilePhoto(uri) }
    }
}
