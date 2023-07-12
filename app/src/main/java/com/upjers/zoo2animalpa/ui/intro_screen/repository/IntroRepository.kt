package com.upjers.zoo2animalpa.ui.intro_screen.repository

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.upjers.zoo2animalpa.model.TrackingData
import com.upjers.zoo2animalpa.services.network.RemoteApi
import com.upjers.zoo2animalpa.services.network.data.ResponseData
import javax.inject.Inject

class IntroRepository @Inject constructor(private val apiService: RemoteApi) {

    suspend fun getData(data: TrackingData) = try {
        apiService.getRemoteData(data)
    } catch (e: Exception) {
        e.printStackTrace()
        FirebaseCrashlytics.getInstance().recordException(e)
        ResponseData() // Default value with = null response or error
    }
}