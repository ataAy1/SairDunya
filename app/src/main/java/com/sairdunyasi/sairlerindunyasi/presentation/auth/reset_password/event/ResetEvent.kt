package com.sairdunyasi.sairlerindunyasi.presentation.auth.reset_password.event

sealed class ResetEvent {
    data class Submit(val email: String) : ResetEvent()
}
