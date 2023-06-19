package com.miniclip.footb.ui.services

import android.content.Context
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class GoogleAdvertisingIdHandler(private val context: Context) {
    suspend fun getGoogleID() = withContext(Dispatchers.IO) {
        suspendCoroutine { continuation ->
            try {
                continuation.resume(AdvertisingIdClient.getAdvertisingIdInfo(context).id)
            } catch (e: Exception) {
                continuation.resume(null)
            }
        }
    }
}