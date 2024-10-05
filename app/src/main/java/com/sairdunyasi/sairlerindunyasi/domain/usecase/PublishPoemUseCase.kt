package com.sairdunyasi.sairlerindunyasi.domain.usecase

import com.sairdunyasi.sairlerindunyasi.domain.model.PoemModel
import com.sairdunyasi.sairlerindunyasi.domain.repository.PoemRepository
import javax.inject.Inject

class PublishPoemUseCase @Inject constructor(
    private val poemRepository: PoemRepository
) {
    suspend fun addPoem(title: String, content: String) {
        return poemRepository.addPoem(title,content)
    }
}
