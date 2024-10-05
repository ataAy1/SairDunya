package com.sairdunyasi.sairlerindunyasi.presentation.profile.published_poems.events

import com.sairdunyasi.sairlerindunyasi.domain.model.PoemModel

sealed class PublishedPoemEvent {
    object FetchPoems : PublishedPoemEvent()
    data class DeletePoem(val poemId: String) : PublishedPoemEvent()
    data class DetailPoem(val poemModel: PoemModel) : PublishedPoemEvent()
}
