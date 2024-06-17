package com.fit.testopenpay

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class ApplicationTestOpenPay : Application() {

    @Inject
    lateinit var firebaseApp: FirebaseApp
    override fun onCreate() {
        super.onCreate()
    }
}