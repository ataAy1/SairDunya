package com.sairdunyasi.sairlerindunyasi.domain.model

import java.io.Serializable


data class PoemModel(
    val id: String = "",
    val siir: String = "",
    val siirBasligi: String = "",
    val userEmail: String = "",
    val date: String = "",
) : Serializable
