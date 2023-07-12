package com.upjers.zoo2animalpa.services.extensions.g_param

import io.github.boiawidmb9mb12095n21b50215b16132.b21nm01om5n1905mw0bdkb2b515.ObfStr
import android.content.Context
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.upjers.zoo2animalpa.services.extensions.g_param.repo.AdvertisingParamRepo
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.boiawidmb9mb12095n21b50215b16132.b21nm01om5n1905mw0bdkb2b515.ObfustringThis
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/* GAID extraction */
@ObfustringThis
class AdvertisingParamImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : AdvertisingParamRepo {
    override suspend fun getAdvertisingID(): String? = withContext(Dispatchers.IO) {
        suspendCoroutine { continuation ->
            try {
                continuation.resume(AdvertisingIdClient.getAdvertisingIdInfo(context).id.toString())
            } catch (e: Exception) {
                continuation.resume(null)
            }
        }
    }
}