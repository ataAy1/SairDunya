package com.sairdunyasi.sairlerindunyasi.data.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sairdunyasi.sairlerindunyasi.data.repository.ProfileRepositoryImpl
import com.sairdunyasi.sairlerindunyasi.data.source.ProfileDataSource
import com.sairdunyasi.sairlerindunyasi.data.source.ProfileDataSourceImpl
import com.sairdunyasi.sairlerindunyasi.domain.repository.ProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {

    @Provides
    @Singleton
    fun provideProfileDataSource(firestore: FirebaseFirestore,firebaseAuth: FirebaseAuth): ProfileDataSource {
        return ProfileDataSourceImpl(firestore,firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(profileDataSource: ProfileDataSource): ProfileRepository {
        return ProfileRepositoryImpl(profileDataSource)
    }

}
