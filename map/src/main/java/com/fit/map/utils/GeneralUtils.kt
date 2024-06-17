package com.fit.map.utils

import android.os.Build
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale


fun getCurrentDateString(): String {
    val currentDateHour = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // Use LocalDateTime for API >= 26
        val now = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        now.format(formatter)
    } else {
        // Use Calendar and SimpleDateFormat for API < 26
        val now = Calendar.getInstance().time
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        formatter.format(now)
    }
    return currentDateHour
}