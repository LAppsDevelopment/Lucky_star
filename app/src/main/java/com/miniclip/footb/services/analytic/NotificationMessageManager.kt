package com.miniclip.footb.services.analytic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.miniclip.footb.di.MyApplication
import com.miniclip.footb.services.local_cache.MyDataStoreImpl
import com.onesignal.OneSignal
import io.github.boiawidmb9mb12095n21b50215b16132.b21nm01om5n1905mw0bdkb2b515.ObfustringThis
import kotlinx.coroutines.runBlocking

@ObfustringThis
object NotificationMessageManager {
    var signalValue: String = NotificationTypes.FIRST_OPEN.description
    var pushIntent: Intent? = null
    val camp: String = "&campaign="
    val subTen: String = "&sub10="

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
                link = changeLink(link)
                putExtra(URL_KEY, link)
            }
            pushIntent = intentToActivity
        }
    }

    private fun cropToCamp(link: String): String = link.substringBefore(camp)

    fun changeLink(link: String): String {
        val mainWithSignal = cropToCamp(link = link).replaceAfter(subTen, signalValue)

        return getChangedUrl(link = link, mainWithSignal = mainWithSignal)
    }

    private fun getChangedUrl(link: String, mainWithSignal: String): String {
        return link.replaceBefore(camp, mainWithSignal)
    }
}