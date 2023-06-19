package com.miniclip.footb.ui.services.params.m_apps_flyer

import android.content.Context
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.miniclip.footb.ui.services.params.m_apps_flyer.repo.MyAppsFlyerRepo
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MyAppsFlyerImpl @Inject constructor(
    @ActivityContext private val activityContext: Context
) : MyAppsFlyerRepo {
    private val appsFlyerLibInstance: AppsFlyerLib = AppsFlyerLib.getInstance().apply {
        start(activityContext, APPS_FLYER_DEV_KEY)
    }

    override val myConversionListener:
                (Continuation<Map<String, String?>?>) -> AppsFlyerConversionListener =
        { continuation ->
            object : AppsFlyerConversionListener {
                override fun onConversionDataSuccess(data: MutableMap<String, Any>?) {
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

    override suspend fun getConversionMap() = suspendCoroutine { continuation ->
        appsFlyerLibInstance.registerConversionListener(
            activityContext,
            myConversionListener(continuation)
        )
    }

    override fun getServiceUID(): String? = try {
        appsFlyerLibInstance.getAppsFlyerUID(activityContext)
    } catch (e: Exception) {
        null
    }

    private fun Continuation<Map<String, String?>?>.customResume(responseMap: Map<String, String?>? = null) {
        resume(responseMap)
    }

    private fun convertMapResponse(data: MutableMap<String, Any>?): Map<String, String?>? =
        data?.entries?.associate { it.key to it.value?.toString() }

    companion object {
        const val APPS_FLYER_DEV_KEY = "CuMkMgVWpTFur6VxbZwqea"
    }
}