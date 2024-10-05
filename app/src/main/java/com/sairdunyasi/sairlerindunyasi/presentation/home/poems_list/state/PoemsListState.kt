package com.sairdunyasi.sairlerindunyasi.presentation.home.poems_list.state

import com.sairdunyasi.sairlerindunyasi.domain.model.PoemWithProfileModel

data class PoemsListState(
    val poems: List<PoemWithProfileModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
