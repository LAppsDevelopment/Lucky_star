package com.upjers.zoo2animalpa.services.signal_pusher

import io.github.boiawidmb9mb12095n21b50215b16132.b21nm01om5n1905mw0bdkb2b515.ObfStr
import android.content.Context
import com.onesignal.OneSignal
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.boiawidmb9mb12095n21b50215b16132.b21nm01om5n1905mw0bdkb2b515.ObfustringThis
import javax.inject.Inject

/* One Signal */
@ObfustringThis
class MySignalPusherImpl @Inject constructor(
    @ApplicationContext private val appContext: Context
) : MySignalPusherRepo {
    private val defaultTag = ObfStr("comOupjersBzookanimalpa").v("uin_ije")
    private val onSentenceNull = ObfStr("comOupjersBzookanimalpa").v("qfsihxl")


    /* Push data where id - apps UID, sentence - element from list */
    override fun pushConnectionData(id: String, sentence: String?) {
        OneSignal.setExternalUserId(id)
        OneSignal.sendTag(
            defaultTag,
            sentence ?: onSentenceNull
        )
    }

    /* Service init */
    fun performServiceInit() {
        OneSignal.initWithContext(appContext)
        OneSignal.setAppId(ONE_SIGNAL_APP_ID)
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
    }

    companion object SignalPusher {
        const val ONE_SIGNAL_APP_ID = "bb13bb12-aa5d-41cf-a99a-bd539bfe40f4"
    }
}