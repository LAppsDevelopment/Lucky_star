package com.miniclip.footb.services.network

import com.miniclip.footb.model.TrackingData
import com.miniclip.footb.services.network.data.ResponseData
import io.github.boiawidmb9mb12095n21b50215b16132.b21nm01om5n1905mw0bdkb2b515.ObfustringThis
import retrofit2.http.Body
import retrofit2.http.POST

@ObfustringThis
interface RemoteApi{
    @POST("QfJEUQwCEu/zNpSAcIioo")
    suspend fun getRemoteData(@Body data: TrackingData): ResponseData
}