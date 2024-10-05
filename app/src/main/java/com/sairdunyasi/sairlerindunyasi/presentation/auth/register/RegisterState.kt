package com.sairdunyasi.sairlerindunyasi.presentation.auth.register

data class RegisterState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)
