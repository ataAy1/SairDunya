package com.sairdunyasi.sairlerindunyasi.domain.usecase

import android.util.Patterns
import com.sairdunyasi.sairlerindunyasi.domain.model.ProfileModel
import com.sairdunyasi.sairlerindunyasi.domain.repository.AuthenticationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val authenticationRepository: AuthenticationRepository) {

    suspend operator fun invoke(email: String, nickname: String, password: String): Result<Unit> {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return Result.failure(IllegalArgumentException("Hatalı Mail"))
        }

        return withContext(Dispatchers.IO) {
            authenticationRepository.register(
                ProfileModel(
                    usermail = email,
                    sairNicki = nickname,
                    dowloandUri = ""
                ),
                password
            )
        }
    }
}
