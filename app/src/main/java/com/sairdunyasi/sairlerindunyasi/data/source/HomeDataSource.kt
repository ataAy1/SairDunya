package com.sairdunyasi.sairlerindunyasi.data.source

import com.sairdunyasi.sairlerindunyasi.domain.model.LikedUserModel
import com.sairdunyasi.sairlerindunyasi.domain.model.PoemModel
import com.sairdunyasi.sairlerindunyasi.domain.model.PoemWithProfileModel
import kotlinx.coroutines.flow.Flow

interface HomeDataSource {
    suspend fun getAllPoems(): Flow<List<PoemWithProfileModel>>
    suspend fun addLike(poemId: String)
    suspend fun removeLike(poemId: String)
    suspend fun fetchUsersWhoLiked(poemId: String):  Flow<List<LikedUserModel>>
}
