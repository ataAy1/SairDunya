package com.sairdunyasi.sairlerindunyasi.data.utils


import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

object DateUtils {

    @RequiresApi(Build.VERSION_CODES.O)
    val TURKEY_ZONE_ID = ZoneId.of("Europe/Istanbul")

    @RequiresApi(Build.VERSION_CODES.O)
    private val TURKISH_LOCALE = Locale("tr", "TR")

    @RequiresApi(Build.VERSION_CODES.O)
     val DATE_FORMATTER = DateTimeFormatter.ofPattern("dd MMMM yyyy", TURKISH_LOCALE)


    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentDateInTurkey(): String {
        val localDate = LocalDate.now(TURKEY_ZONE_ID)
        return localDate.format(DATE_FORMATTER)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDateInTurkey(date: LocalDate): String {
        return date.format(DATE_FORMATTER)
    }
}
