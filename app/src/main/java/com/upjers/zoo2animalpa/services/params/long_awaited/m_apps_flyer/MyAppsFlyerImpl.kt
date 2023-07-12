package com.upjers.zoo2animalpa.services.params.long_awaited.m_apps_flyer

import io.github.boiawidmb9mb12095n21b50215b16132.b21nm01om5n1905mw0bdkb2b515.ObfStr
import android.content.Context
import android.util.Log
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.upjers.zoo2animalpa.services.params.long_awaited.m_apps_flyer.repo.MyAppsFlyerRepo
import dagger.hilt.android.qualifiers.ActivityContext
import io.github.boiawidmb9mb12095n21b50215b16132.b21nm01om5n1905mw0bdkb2b515.ObfustringThis
import javax.inject.Inject
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@ObfustringThis
class MyAppsFlyerImpl @Inject constructor(
    @ActivityContext private val activityContext: Context
) : MyAppsFlyerRepo {
      override val myConversionListener:
                (Continuation<Map<String, String?>?>) -> AppsFlyerConversionListener =
        { continuation ->
            object : AppsFlyerConversionListener {
                override fun onConversionDataSuccess(data: MutableMap<String, Any>?) {
//@ | Log.e("MyAppsFlyerImpl", "onConversionDataSuccess: $data")
                    Log.e(ObfStr("comOupjersBzookanimalpa").v("ImGxjhIppwmBadv"), ObfStr("comOupjersBzookanimalpa").v("qbIwhknvjajmLodaZcocphs: ¦$data¦"))
                    continuation.customResume(convertMapResponse(data))
                }

                override fun onConversionDataFail(error: String?) {
                    continuation.customResume()
                }

                override fun onAppOpenAttribution(data: MutableMap<String, String>?) {
                    continuation.customResume()
                }

                override fun onAttributionFailure(error: String?) {
                    continuation.customResume()
                }
            }
        }

    // Map extraction
    override suspend fun getConversionMap() = suspendCoroutine { continuation ->
        AppsFlyerLib.getInstance().init(
            APPS_FLYER_DEV_KEY,
            myConversionListener(continuation),
            activityContext
        ).start(activityContext)
    }

    //UID extraction
    override fun getServiceUID(): String? = try {
        AppsFlyerLib.getInstance().getAppsFlyerUID(activityContext)
    } catch (e: Exception) {
        null
    }

    private fun Continuation<Map<String, String?>?>.customResume(responseMap: Map<String, String?>? = null) {
        resume(responseMap)
    }

    private fun convertMapResponse(data: MutableMap<String, Any>?): Map<String, String?>? =
        data?.entries?.associate { it.key to it.value?.toString() }

    companion object {
        val APPS_FLYER_DEV_KEY = ObfStr("comOupjersBzookanimalpa").v("YiSsAvYUgFUtf6DhbGecel")
    }
}