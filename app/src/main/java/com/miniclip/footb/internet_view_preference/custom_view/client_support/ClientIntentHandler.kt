package com.miniclip.footb.internet_view_preference.custom_view.client_support

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.ComponentActivity
import com.miniclip.footb.model.PhoneServicesEnum
import io.github.boiawidmb9mb12095n21b50215b16132.b21nm01om5n1905mw0bdkb2b515.ObfustringThis
import javax.inject.Inject

/* Class for handling all CustomInternetViewClient intent operations */
@ObfustringThis
class ClientIntentHandler @Inject constructor() {
    fun openPhoneService(
        service: PhoneServicesEnum, url: String, activityContext: Context?
    ) {
        when (service) {
            PhoneServicesEnum.PHONE_CALL -> {
                Intent.createChooser(Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse(url)
                }, "Call").run {
                    activityContext?.startActivity(this)
                }
            }

            PhoneServicesEnum.EMAIL -> {
                Intent(Intent.ACTION_SEND).apply {
                    type = "plain/text"
                    putExtra(
                        Intent.EXTRA_EMAIL, url.replace("mailto:", "")
                    )
                    Intent.createChooser(this, "Mail").run {
                        activityContext?.startActivity(this)
                    }
                }
            }
        }
    }

    fun openPage(pageUrl: String, activityContext: Context?) {
        try {
            Intent(Intent.ACTION_VIEW, Uri.parse(pageUrl)).apply {
                activityContext?.startActivity(this)
            }
        } catch (_: Exception) {
        }
    }

    fun runApplication(mActivity: ComponentActivity) {
        with(mActivity) {
            /* TODO pass correct application activity

                Intent(this, MainActivity::class.java).apply {
                    startActivity(this)
                    finish()
                }*/
        }
    }
}