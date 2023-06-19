package com.miniclip.footb.ui.services.signal_pusher

import android.content.Context
import com.onesignal.OneSignal
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/* One Signal */
class MySignalPusherImpl @Inject constructor(
    @ApplicationContext private val appContext: Context
) : MySignalPusherRepo {
    private val defaultTag = "sub_app"
    private val onSentenceNull = "organic"

    init {
        performServiceInit()
    }

    /* Push data where id - apps UID, sentence - element from list */
    override fun pushConnectionData(id: String, sentence: String?) {
        OneSignal.setExternalUserId(id)
        OneSignal.sendTag(
            defaultTag,
            sentence ?: onSentenceNull
        )
    }

    /* Service init */
    private fun performServiceInit() {
        OneSignal.initWithContext(appContext)
        OneSignal.setAppId(ONE_SIGNAL_APP_ID)
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
    }

    companion object SignalPusher {
        const val ONE_SIGNAL_APP_ID = "bb13bb12-aa5d-41cf-a99a-bd539bfe40f4"
    }
}