package com.sairdunyasi.sairlerindunyasi.presentation.home.poem_liked.state

import com.sairdunyasi.sairlerindunyasi.domain.model.LikedUserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class LikedUsersState(
    val users: Flow<List<LikedUserModel>> = flowOf(emptyList()),
    val isLoading: Boolean = false,
    val error: String? = null
)
