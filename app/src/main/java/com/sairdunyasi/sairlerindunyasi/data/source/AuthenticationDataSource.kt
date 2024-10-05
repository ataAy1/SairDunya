package com.sairdunyasi.sairlerindunyasi.data.source

import com.sairdunyasi.sairlerindunyasi.domain.model.ProfileModel

interface AuthenticationDataSource {
    suspend fun login(email: String, password: String): Result<Unit>
    suspend fun logout(): Result<Unit>
    suspend fun register(userProfile: ProfileModel, password: String): Result<Unit>
    suspend fun resetPassword(email: String): Result<Unit>
}
