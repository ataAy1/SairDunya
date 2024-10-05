package com.sairdunyasi.sairlerindunyasi.domain.repository

import com.sairdunyasi.sairlerindunyasi.domain.model.PoemModel
import kotlinx.coroutines.flow.Flow

interface PoemRepository {
    suspend fun getUserPoems(): Flow<List<PoemModel>>
    suspend fun deletePoem(poemId: String): Boolean
    suspend fun addPoem(title: String, content: String)
}