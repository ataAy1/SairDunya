package com.sairdunyasi.sairlerindunyasi.presentation.home.home_poems.events

import com.sairdunyasi.sairlerindunyasi.domain.model.PoemWithProfileModel

sealed class HomeEvents {
    data class OnFavoriteToggle(val poemId: String) : HomeEvents()
    object OnNextPoemClick : HomeEvents()
    object OnPreviousPoemClick : HomeEvents()

}
