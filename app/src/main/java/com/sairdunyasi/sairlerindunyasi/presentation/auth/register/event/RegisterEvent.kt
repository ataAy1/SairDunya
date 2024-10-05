package com.sairdunyasi.sairlerindunyasi.presentation.auth.register.event

sealed class RegisterEvent {
    data class Submit(val email: String, val nickname: String, val password: String) : RegisterEvent()
}
