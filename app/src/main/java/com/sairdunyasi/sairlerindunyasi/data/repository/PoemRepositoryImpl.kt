package com.sairdunyasi.sairlerindunyasi.data.repository

import com.sairdunyasi.sairlerindunyasi.data.source.PoemDataSource
import com.sairdunyasi.sairlerindunyasi.domain.model.PoemModel
import com.sairdunyasi.sairlerindunyasi.domain.repository.PoemRepository
import kotlinx.coroutines.flow.Flow

class PoemRepositoryImpl(
    private val poemDataSource: PoemDataSource
) : PoemRepository {
    override suspend fun getUserPoems(): Flow<List<PoemModel>> {
        return poemDataSource.getUserPoems()
    }

    override suspend fun deletePoem(poemId: String): Boolean {
        return poemDataSource.deletePoem(poemId)
    }

    override suspend fun addPoem(title: String, content: String) {
        poemDataSource.addPoem(title, content)
    }
}