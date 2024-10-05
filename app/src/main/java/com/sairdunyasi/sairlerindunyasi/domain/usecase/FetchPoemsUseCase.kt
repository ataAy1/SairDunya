package com.sairdunyasi.sairlerindunyasi.domain.usecase

import com.sairdunyasi.sairlerindunyasi.domain.model.PoemModel
import com.sairdunyasi.sairlerindunyasi.domain.model.PoemWithProfileModel
import com.sairdunyasi.sairlerindunyasi.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchPoemsUseCase @Inject constructor(private val homeRepository: HomeRepository) {
    suspend operator fun invoke(): Flow<List<PoemWithProfileModel>> {
        return homeRepository.getAllPoems()
    }
}
