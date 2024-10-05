package com.sairdunyasi.sairlerindunyasi.presentation.home.home_poems.state

import com.sairdunyasi.sairlerindunyasi.domain.model.PoemWithProfileModel

data class HomeState(
    val poems: List<PoemWithProfileModel> = emptyList(),
    val currentPoem: PoemWithProfileModel? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
