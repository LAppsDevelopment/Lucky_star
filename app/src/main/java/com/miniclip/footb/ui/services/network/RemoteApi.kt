package com.miniclip.footb.ui.services.network

import com.miniclip.footb.ui.model.TrackingData
import com.miniclip.footb.ui.services.network.data.RemoteResponse
import io.github.boiawidmb9mb12095n21b50215b16132.b21nm01om5n1905mw0bdkb2b515.ObfustringThis
import retrofit2.http.Body
import retrofit2.http.POST

@ObfustringThis
interface RemoteApi {
    @POST("12345U/X56789")
    suspend fun getRemoteData(@Body data: TrackingData): RemoteResponse
}