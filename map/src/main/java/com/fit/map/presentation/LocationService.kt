package com.fit.map.presentation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.fit.map.R
import com.fit.map.domain.model.LocationModel
import com.fit.map.domain.usecase.GetLocationUseCase
import com.fit.map.domain.usecase.SendLocationFirestoreUseCase
import com.fit.map.utils.getCurrentDateString
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class LocationService : Service() {

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val binder = LocationBinder()
    private var isAppInForeground: Boolean = true
    private var callback: LocationCallback? = null

    @Inject
    lateinit var getLocationUseCase: GetLocationUseCase

    @Inject
    lateinit var sendLocationFirestoreUseCase: SendLocationFirestoreUseCase

    inner class LocationBinder : Binder() {
        fun getService(): LocationService = this@LocationService
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    interface LocationCallback {
        fun onLocationUpdate(latitude: Double, longitude: Double)
    }

    fun registerCallback(callback: LocationCallback?) {
        this.callback = callback
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createChannel()
        when (intent?.action) {
            ACTION_START -> start()
            ACTION_STOP -> stop()
        }
        return START_STICKY
    }

    private fun start() {
        val notification = NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Tracking location...")
            .setContentText("Location...")
            .setSmallIcon(R.drawable.ic_location_pin_24)
            .setAutoCancel(true)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        getLocationUseCase.start().catch {
            Log.d("LocationService", it.message.toString())
        }.onEach { location ->
            val lat = location.latitude.toString()
            val long = location.longitude.toString().takeLast(3)
            val updatedNotification = notification.setContentText(
                "Location: ($lat, $long)"
            )
            Log.i("TEST", "Location: ($lat, $long)")
            if (!isAppInForeground) {
                notificationManager.notify(NOTIFICATION_ID, updatedNotification.build())
            } else {
                notificationManager.cancel(NOTIFICATION_ID)
            }

            callback?.onLocationUpdate(location.latitude, location.longitude) ?: run {
                val request = LocationModel(
                    "Location",
                    getCurrentDateString(),
                    location.latitude,
                    location.longitude
                )
                sendLocationFirestoreUseCase(request)
            }
        }.launchIn(serviceScope)
        startForeground(NOTIFICATION_ID, notification.build())
    }

    private fun stop() {
        getLocationUseCase.stop()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            stopForeground(STOP_FOREGROUND_REMOVE)
        } else {
            stopForeground(true)
        }
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Location Service",
                NotificationManager.IMPORTANCE_HIGH
            )

            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    fun setAppInForeground(isInForeground: Boolean) {
        isAppInForeground = isInForeground
    }

    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
        private const val NOTIFICATION_ID = 1
        private const val NOTIFICATION_CHANNEL_ID = "testopenpay_location_channel"
    }
}