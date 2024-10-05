package com.sairdunyasi.sairlerindunyasi.presentation.auth.reset_password

data class ResetState(
    val isLoading: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null
)
