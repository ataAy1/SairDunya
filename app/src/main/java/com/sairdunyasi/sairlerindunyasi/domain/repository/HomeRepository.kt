package com.sairdunyasi.sairlerindunyasi.domain.repository

import com.sairdunyasi.sairlerindunyasi.domain.model.LikedUserModel
import com.sairdunyasi.sairlerindunyasi.domain.model.PoemWithProfileModel
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getAllPoems(): Flow<List<PoemWithProfileModel>>
    suspend fun addLike(poemId: String)
    suspend fun removeLike(poemId: String)
    suspend fun fetchUsersWhoLiked(poemId: String):  Flow<List<LikedUserModel>>
}
