package com.sairdunyasi.sairlerindunyasi.presentation.profile.publish_poem.event

data class PublishPoemState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)
