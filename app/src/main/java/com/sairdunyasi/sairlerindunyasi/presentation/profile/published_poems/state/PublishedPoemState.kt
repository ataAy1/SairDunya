package com.sairdunyasi.sairlerindunyasi.presentation.profile.published_poems.state

import com.sairdunyasi.sairlerindunyasi.domain.model.PoemModel

sealed class PublishedPoemState {
    object Loading : PublishedPoemState()
    data class Success(val poems: List<PoemModel>) : PublishedPoemState()
    data class Error(val message: String) : PublishedPoemState()
    object DeletionSuccess : PublishedPoemState()
}