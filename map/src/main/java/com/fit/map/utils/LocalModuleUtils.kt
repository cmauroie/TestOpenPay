package com.fit.map.utils

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Build
import android.provider.Settings
import com.fit.core.utils.openAppSettings
import com.fit.map.R
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

fun showPermissionDeniedDialog(context: Context) {
    AlertDialog.Builder(context)
        .setTitle(context.getString(R.string.title_alert_permission_location))
        .setMessage(R.string.alert_message_location)
        .setPositiveButton(R.string.btn_positive) { dialog, which ->
            openAppSettings(context)
        }
        .setNegativeButton(R.string.btn_negative, null)
        .show()
}



fun showGpsDisabledDialog(context: Context) {
    AlertDialog.Builder(context)
        .setTitle(R.string.title_alert_gps_location)
        .setMessage(R.string.alert_message_gps)
        .setPositiveButton(R.string.btn_positive) { dialog, which ->
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            context.startActivity(intent)
        }
        .setNegativeButton(R.string.btn_negative, null)
        .show()
}

fun isLocationEnabled(context: Context): Boolean {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    return isGpsEnabled || isNetworkEnabled
}