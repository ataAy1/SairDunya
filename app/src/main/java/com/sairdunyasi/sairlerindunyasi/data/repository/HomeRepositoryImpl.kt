package com.sairdunyasi.sairlerindunyasi.data.repository

import com.sairdunyasi.sairlerindunyasi.data.source.HomeDataSource
import com.sairdunyasi.sairlerindunyasi.domain.model.LikedUserModel
import com.sairdunyasi.sairlerindunyasi.domain.model.PoemWithProfileModel
import com.sairdunyasi.sairlerindunyasi.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow

class HomeRepositoryImpl(private val dataSource: HomeDataSource) : HomeRepository {
    override suspend fun getAllPoems(): Flow<List<PoemWithProfileModel>> {
        return dataSource.getAllPoems()
    }

    override suspend fun addLike(poemId: String) {
        dataSource.addLike(poemId)
    }

    override suspend fun removeLike(poemId: String) {
        dataSource.removeLike(poemId)
    }

    override suspend fun fetchUsersWhoLiked(poemId: String): Flow<List<LikedUserModel>> {
        return dataSource.fetchUsersWhoLiked(poemId)
    }
}
