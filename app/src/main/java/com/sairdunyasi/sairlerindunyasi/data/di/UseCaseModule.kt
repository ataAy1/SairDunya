package com.sairdunyasi.sairlerindunyasi.data.di


import com.sairdunyasi.sairlerindunyasi.data.repository.ProfileRepositoryImpl
import com.sairdunyasi.sairlerindunyasi.domain.repository.AuthenticationRepository
import com.sairdunyasi.sairlerindunyasi.domain.repository.ProfileRepository
import com.sairdunyasi.sairlerindunyasi.domain.usecase.LoginUseCase
import com.sairdunyasi.sairlerindunyasi.domain.usecase.ProfileUseCase
import com.sairdunyasi.sairlerindunyasi.domain.usecase.RegisterUseCase
import com.sairdunyasi.sairlerindunyasi.domain.usecase.ResetMailUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideLoginUseCase(authenticationRepository: AuthenticationRepository): LoginUseCase {
        return LoginUseCase(authenticationRepository)

    }

    @Provides
    @Singleton
    fun provideProfileUseCase(profileRepository: ProfileRepository): ProfileUseCase {
        return ProfileUseCase(profileRepository)

    }

    @Provides
    @Singleton
    fun provideRegisterUseCase(authenticationRepository: AuthenticationRepository): RegisterUseCase {
        return RegisterUseCase(authenticationRepository)
    }

    @Provides
    @Singleton
    fun provideResetUseCase(authenticationRepository: AuthenticationRepository): ResetMailUseCase {
        return ResetMailUseCase(authenticationRepository)
    }

}