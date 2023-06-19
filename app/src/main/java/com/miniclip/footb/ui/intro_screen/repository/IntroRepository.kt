package com.miniclip.footb.ui.intro_screen.repository

import com.miniclip.footb.ui.model.TrackingData
import com.miniclip.footb.ui.services.network.RemoteApi
import javax.inject.Inject

class IntroRepository @Inject constructor(private val apiService: RemoteApi) {

    suspend fun getData(data: TrackingData) = apiService.getRemoteData(data)

}