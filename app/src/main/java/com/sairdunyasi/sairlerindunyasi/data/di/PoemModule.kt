package com.sairdunyasi.sairlerindunyasi.data.di

import com.sairdunyasi.sairlerindunyasi.domain.repository.PoemRepository
import com.sairdunyasi.sairlerindunyasi.domain.usecase.PublishedPoemsUseCase
import com.sairdunyasi.sairlerindunyasi.data.repository.PoemRepositoryImpl
import com.sairdunyasi.sairlerindunyasi.data.source.PoemDataSource
import com.sairdunyasi.sairlerindunyasi.data.source.PoemDataSourceImpl
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PoemModule {

    @Provides
    @Singleton
    fun providePoemDataSource(
        firestore: FirebaseFirestore,
        firebaseAuth: FirebaseAuth
    ): PoemDataSource {
        return PoemDataSourceImpl(firestore, firebaseAuth)
    }

    @Provides
    @Singleton
    fun providePoemRepository(
        poemDataSource: PoemDataSource
    ): PoemRepository {
        return PoemRepositoryImpl(poemDataSource)
    }

    @Provides
    @Singleton
    fun providePublishedPoemsUseCase(
        poemRepository: PoemRepository
    ): PublishedPoemsUseCase {
        return PublishedPoemsUseCase(poemRepository)
    }
}
