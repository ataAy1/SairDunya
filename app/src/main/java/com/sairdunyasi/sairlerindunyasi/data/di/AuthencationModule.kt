package com.sairdunyasi.sairlerindunyasi.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sairdunyasi.sairlerindunyasi.data.repository.AuthenticationRepositoryImpl
import com.sairdunyasi.sairlerindunyasi.data.source.AuthenticationDataSource
import com.sairdunyasi.sairlerindunyasi.data.source.AuthenticationDataSourceImpl
import com.sairdunyasi.sairlerindunyasi.domain.repository.AuthenticationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthencationModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideAuthenticationDataSource(firebaseAuth: FirebaseAuth,firebaseFirestore: FirebaseFirestore): AuthenticationDataSource {
        return AuthenticationDataSourceImpl(firebaseAuth,firebaseFirestore)
    }

    @Provides
    @Singleton
    fun provideAuthenticationRepository(
        authenticationDataSource: AuthenticationDataSource
    ): AuthenticationRepository {
        return AuthenticationRepositoryImpl(authenticationDataSource)
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }
}
