package com.miniclip.footb.ui.services

import android.content.Context
import com.facebook.FacebookSdk
import com.facebook.applinks.AppLinkData
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MyFbImpl @Inject constructor(
    @ApplicationContext val context: Context
) : MyFbRepo {
    override suspend fun getFetchedDeepLink(id: String?, token: String?): String? =
        suspendCoroutine { deepLinkContinuation ->
            runInit(id, token)

            AppLinkData.fetchDeferredAppLinkData(context) {
                deepLinkContinuation.resume(it?.targetUri?.toString())
            }
        }

    @Suppress("DEPRECATION")
    private fun runInit(id: String?, token: String?) {
        FacebookSdk.apply {
            fun initBooleanParams(enable: Boolean = true) {
                setAdvertiserIDCollectionEnabled(enable)
                setAutoInitEnabled(enable)
            }

            setFacebookInitParams(id, token)

            sdkInitialize(context)
            initBooleanParams()

            fullyInitialize()
        }
    }

    private fun FacebookSdk.setFacebookInitParams(id: String?, token: String?) {
        setApplicationId(id ?: APP_ID)
        setClientToken(token ?: TOKEN)
    }

    companion object FacebookConstants {
        const val APP_ID = "966958804612479"
        const val TOKEN = "h9o7yv_bD5lAL8YY_FfACnTwWHA"
        const val DECRYPTION_KEY =
            "233f138260d0350d8278312e51bf95517fb805d9648c9968f834bae882cbdfdb"
    }
}
