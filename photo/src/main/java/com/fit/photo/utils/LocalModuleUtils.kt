package com.fit.photo.utils

import android.app.AlertDialog
import android.content.Context
import com.fit.core.utils.openAppSettings
import com.fit.photo.R


fun showPermissionDeniedDialog(context: Context) {
    AlertDialog.Builder(context)
        .setTitle(context.getString(R.string.title_alert_permission))
        .setMessage(R.string.alert_message_photo)
        .setPositiveButton(R.string.btn_positive) { dialog, which ->
           openAppSettings(context)
        }
        .setNegativeButton(R.string.btn_negative, null)
        .show()
}

