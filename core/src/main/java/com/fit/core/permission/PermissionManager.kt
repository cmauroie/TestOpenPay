package com.fit.core.permission

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class PermissionManager @Inject constructor(@ActivityContext private val context: Context) {

    interface PermissionCallback {
        fun onPermissionGranted()
        fun onPermissionDenied()
    }

    private var callback: PermissionCallback? = null
    private var requestCode: Int = 0

    fun requestPermissions(
        permissions: Array<String>,
        requestCode: Int,
        callback: PermissionCallback
    ) {
        this.callback = callback
        this.requestCode = requestCode

        val permissionsToRequest = permissions.filter {
            ContextCompat.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED
        }

        if (permissionsToRequest.isEmpty()) {
            callback.onPermissionGranted()
        } else {
            ActivityCompat.requestPermissions(
                context as Activity,
                permissionsToRequest.toTypedArray(),
                requestCode
            )
        }
    }


    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (this.requestCode == requestCode) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                callback?.onPermissionGranted()
            } else {
                callback?.onPermissionDenied()
            }
        }
    }
}