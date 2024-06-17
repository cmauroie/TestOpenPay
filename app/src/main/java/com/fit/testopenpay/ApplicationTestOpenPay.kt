package com.fit.testopenpay

import android.app.Application
import com.google.firebase.FirebaseApp
//import com.google.firebase.appcheck.FirebaseAppCheck
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class ApplicationTestOpenPay : Application() {

    @Inject
    lateinit var firebaseApp: FirebaseApp

    //@Inject
    //lateinit var firebaseAppCheck: FirebaseAppCheck
    override fun onCreate() {
        super.onCreate()
    }
}