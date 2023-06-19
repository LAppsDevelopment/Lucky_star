package com.miniclip.footb.ui.services.analytic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.miniclip.footb.ui.di.MyApplication
import com.miniclip.footb.ui.services.local_cache.MyDataStoreImpl
import com.onesignal.OneSignal
import io.github.boiawidmb9mb12095n21b50215b16132.b21nm01om5n1905mw0bdkb2b515.ObfustringThis
import kotlinx.coroutines.runBlocking

@ObfustringThis
object NotificationMessageManager {
    var signalValue: String = NotificationTypes.FIRST_OPEN.description
    var pushIntent: Intent? = null

    const val URL_KEY = "URL_NOTIFY_KEY"

    inline fun <reified T : AppCompatActivity> setup(
        application: MyApplication,
        dataStore: MyDataStoreImpl
    ) {
        OneSignal.setNotificationOpenedHandler {
            signalValue = NotificationTypes.PUSH_OPEN.description

            var link = runBlocking { dataStore.getUrl() } ?: ""

            val intentToActivity = Intent(application, T::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                        Intent.FLAG_ACTIVITY_NEW_TASK
                link = link.changeLink()
                putExtra(URL_KEY, link)
            }
            pushIntent = intentToActivity
        }
    }

    fun String.changeLink(): String {
        val sub = this.substringBefore("&campaign=").substringAfter("&sub10=")
        return if (sub == "sub10")
            this.replace("&sub10=sub10", "&sub10=$signalValue")
        else if (sub.isNotEmpty())
            this.replace(sub, signalValue)
        else
            this.replace("&sub10=", "&sub10=$signalValue")
    }
}