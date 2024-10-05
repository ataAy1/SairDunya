package com.sairdunyasi.sairlerindunyasi.domain.usecase

import com.sairdunyasi.sairlerindunyasi.domain.repository.AuthenticationRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke() {
        authenticationRepository.logout()
    }
}