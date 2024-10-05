package com.sairdunyasi.sairlerindunyasi.domain.usecase

import com.sairdunyasi.sairlerindunyasi.domain.repository.AuthenticationRepository
import javax.inject.Inject

class ResetMailUseCase @Inject constructor(private val repository: AuthenticationRepository) {
    suspend fun execute(email: String): Result<Unit> {
        return repository.resetPassword(email)
    }
}
