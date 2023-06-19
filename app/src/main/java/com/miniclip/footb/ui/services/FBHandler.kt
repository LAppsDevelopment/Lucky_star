package com.miniclip.footb.ui.services

import android.content.Context
import com.facebook.FacebookSdk
import com.facebook.applinks.AppLinkData
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FBHandler(private val context: Context) {

    @Suppress("DEPRECATION")
    suspend fun getLeapDeeplink(id: String?, token: String?) =
        suspendCoroutine { coc ->
            FacebookSdk.apply {
                setApplicationId(id ?: APP_ID)
                setClientToken(token ?: TOKEN)
                sdkInitialize(context)
                setAdvertiserIDCollectionEnabled(true)
                setAutoInitEnabled(true)
                fullyInitialize()
            }
            AppLinkData.fetchDeferredAppLinkData(context) {
                coc.resume(it?.targetUri?.toString())
            }
        }

    companion object FacebookConstants {
        const val APP_ID = "966958804612479"
        const val TOKEN = "h9o7yv_bD5lAL8YY_FfACnTwWHA"
        const val DECRYPTION_KEY =
            "233f138260d0350d8278312e51bf95517fb805d9648c9968f834bae882cbdfdb"
    }
}