package com.miniclip.footb.ui.services.extensions.g_param

import android.content.Context
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.miniclip.footb.ui.services.extensions.g_param.repo.AdvertisingParamRepo
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/* GAID extraction */
class AdvertisingParamImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : AdvertisingParamRepo {
    override suspend fun getAdvertisingID(): String? = withContext(Dispatchers.IO) {
        suspendCoroutine { continuation ->
            try {
                continuation.resume(AdvertisingIdClient.getAdvertisingIdInfo(context).id)
            } catch (e: Exception) {
                continuation.resume(null)
            }
        }
    }
}