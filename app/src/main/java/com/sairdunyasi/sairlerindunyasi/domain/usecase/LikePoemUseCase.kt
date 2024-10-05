package com.sairdunyasi.sairlerindunyasi.domain.usecase



import android.util.Log
import com.sairdunyasi.sairlerindunyasi.domain.repository.HomeRepository
import javax.inject.Inject

class LikePoemUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(poemId: String, isFavorite: Boolean) {
        if (isFavorite==true) {
            homeRepository.addLike(poemId)
        } else {
            homeRepository.removeLike(poemId)

        }
    }
}
