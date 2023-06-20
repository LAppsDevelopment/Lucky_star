package com.miniclip.footb.ui.intro_screen.repository

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.miniclip.footb.ui.model.TrackingData
import com.miniclip.footb.ui.services.network.RemoteApi
import com.miniclip.footb.ui.services.network.data.RemoteResponse
import javax.inject.Inject

class IntroRepository @Inject constructor(private val apiService: RemoteApi) {

    suspend fun getData(data: TrackingData) = try {
        apiService.getRemoteData(data)
    } catch (e: Exception) {
        e.printStackTrace()
        FirebaseCrashlytics.getInstance().recordException(e)
        RemoteResponse() // Default value with = null response or error
    }

}