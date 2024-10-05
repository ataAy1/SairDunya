package com.sairdunyasi.sairlerindunyasi.data.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sairdunyasi.sairlerindunyasi.data.repository.HomeRepositoryImpl
import com.sairdunyasi.sairlerindunyasi.data.source.HomeDataSource
import com.sairdunyasi.sairlerindunyasi.data.source.HomeDataSourceImpl
import com.sairdunyasi.sairlerindunyasi.domain.repository.HomeRepository
import com.sairdunyasi.sairlerindunyasi.domain.usecase.FetchPoemsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {

    @Provides
    @Singleton
    fun provideHomeDataSource(firestore: FirebaseFirestore,firebaseAuth: FirebaseAuth): HomeDataSource {
        return HomeDataSourceImpl(firestore,firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideHomeRepository(homeDataSource: HomeDataSource): HomeRepository {
        return HomeRepositoryImpl(homeDataSource)
    }


    @Provides
    @Singleton
    fun provideFetchPoemsUseCase(repository: HomeRepository): FetchPoemsUseCase {
        return FetchPoemsUseCase(repository)
    }
}
