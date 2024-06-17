package com.fit.map.domain.manager

import com.google.firebase.FirebaseApp

interface FirebaseManager {
    fun initializeApp()
    fun getInstance(): FirebaseApp
}