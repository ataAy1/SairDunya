package com.sairdunyasi.sairlerindunyasi.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@kotlinx.serialization.Serializable
data class PoemWithProfileModel(
    val id: String = "",
    val siir: String = "",
    val siirBasligi: String = "",
    val date: String = "",
    val userNick: String = "",
    val userProfilePhoto: String = "",
    val userEmail: String = "",
    val isFavorite: Boolean = false,
    var counterFavNumber: Int
) : Serializable
