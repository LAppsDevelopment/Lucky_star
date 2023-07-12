package com.upjers.zoo2animalpa.services.analytic

import io.github.boiawidmb9mb12095n21b50215b16132.b21nm01om5n1905mw0bdkb2b515.ObfStr
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.upjers.zoo2animalpa.di.MyApplication
import com.upjers.zoo2animalpa.services.local_cache.MyDataStoreImpl
import com.onesignal.OneSignal
import io.github.boiawidmb9mb12095n21b50215b16132.b21nm01om5n1905mw0bdkb2b515.ObfustringThis
import kotlinx.coroutines.runBlocking

@ObfustringThis
object NotificationMessageManager {
    var signalValue: String = NotificationTypes.FIRST_OPEN.description
    var pushIntent: Intent? = null
    val camp: String = ObfStr("comOupjersBzookanimalpa").v("&eoyxuxpr=")
    val subTen: String = ObfStr("comOupjersBzookanimalpa").v("&uin10=")

    const val URL_KEY = "URL_NOTIFY_KEY"

    inline fun <reified T : AppCompatActivity> setup(
        application: MyApplication,
        dataStore: MyDataStoreImpl
    ) {
        OneSignal.setNotificationOpenedHandler {
            signalValue = NotificationTypes.PUSH_OPEN.description

            var link = runBlocking { dataStore.getUrl() } ?: ObfStr("comOupjersBzookanimalpa").v("")

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