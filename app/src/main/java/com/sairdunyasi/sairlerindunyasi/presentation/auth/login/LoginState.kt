package com.sairdunyasi.sairlerindunyasi.presentation.auth.login

data class LoginState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)
