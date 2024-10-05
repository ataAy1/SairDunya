package com.sairdunyasi.sairlerindunyasi.domain.usecase

import com.sairdunyasi.sairlerindunyasi.domain.model.LikedUserModel
import com.sairdunyasi.sairlerindunyasi.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchUsersWhoLikedPoemUseCase @Inject constructor(
    private val repository: HomeRepository
) {
    suspend operator fun invoke(poemId: String): Flow<List<LikedUserModel>> {
        return repository.fetchUsersWhoLiked(poemId)
    }
}
