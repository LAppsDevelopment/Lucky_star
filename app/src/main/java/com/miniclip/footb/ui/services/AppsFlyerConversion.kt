package com.miniclip.footb.ui.services

import androidx.activity.ComponentActivity
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AppsFlyerConversion(private val activity: ComponentActivity) {
    val conversionListener: (Continuation<Map<String, String?>?>) -> AppsFlyerConversionListener =
        { continuation ->

            object : AppsFlyerConversionListener {
                override fun onConversionDataSuccess(data: MutableMap<String, Any>?) {
                    val map = data?.entries?.associate { it.key to it.value.toString() }
                    continuation.resume(map)
                }

                override fun onConversionDataFail(error: String?) {
                    continuation.resume(null)
                }

                override fun onAppOpenAttribution(data: MutableMap<String, String>?) {
                    continuation.resume(null)
                }

                override fun onAttributionFailure(error: String?) {
                    continuation.resume(null)
                }
            }
        }


    private val appsFlyerLib = AppsFlyerLib.getInstance().apply {
        start(activity, DEV_KEY)
    }

    suspend fun getConversion() = suspendCoroutine { continuation ->
        appsFlyerLib.registerConversionListener(activity, conversionListener(continuation))
    }

    fun getLeapUserID() = try {
        appsFlyerLib.getAppsFlyerUID(activity)
    } catch (e: Exception) {
        null
    }

    companion object {
        const val DEV_KEY = "CuMkMgVWpTFur6VxbZwqea"
    }
}