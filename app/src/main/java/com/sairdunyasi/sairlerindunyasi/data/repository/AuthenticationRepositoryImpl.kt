package com.sairdunyasi.sairlerindunyasi.data.repository

import com.sairdunyasi.sairlerindunyasi.data.source.AuthenticationDataSource
import com.sairdunyasi.sairlerindunyasi.domain.model.ProfileModel
import com.sairdunyasi.sairlerindunyasi.domain.repository.AuthenticationRepository

class AuthenticationRepositoryImpl(
    private val authenticationDataSource: AuthenticationDataSource
) : AuthenticationRepository {

    override suspend fun login(email: String, password: String): Result<Unit> {
        return authenticationDataSource.login(email, password)
    }

    override suspend fun logout(): Result<Unit> {
        return authenticationDataSource.logout()
    }

    override suspend fun register(userProfile: ProfileModel, password: String): Result<Unit> {
        return authenticationDataSource.register(userProfile, password)
    }
    override suspend fun resetPassword(email: String): Result<Unit> {
        return authenticationDataSource.resetPassword(email)
    }
}
