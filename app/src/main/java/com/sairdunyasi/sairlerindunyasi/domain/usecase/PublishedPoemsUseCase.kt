package com.sairdunyasi.sairlerindunyasi.domain.usecase


import com.sairdunyasi.sairlerindunyasi.domain.model.PoemModel
import com.sairdunyasi.sairlerindunyasi.domain.repository.PoemRepository
import kotlinx.coroutines.flow.Flow


import javax.inject.Inject

class PublishedPoemsUseCase @Inject constructor(
    private val poemRepository: PoemRepository
) {
    suspend fun getUserPoems(): Flow<List<PoemModel>> {
        return poemRepository.getUserPoems()
    }

    suspend fun deletePoem(poemId: String) {
        poemRepository.deletePoem(poemId)
    }
}
