package com.upjers.zoo2animalpa.internet_view_preference.custom_view.client_support

import io.github.boiawidmb9mb12095n21b50215b16132.b21nm01om5n1905mw0bdkb2b515.ObfStr
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.ComponentActivity
import com.upjers.zoo2animalpa.model.PhoneServicesEnum
import com.upjers.zoo2animalpa.ui.host.AppContainerActivity
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
                }, ObfStr("comOupjersBzookanimalpa").v("Yoxt")).run {
                    activityContext?.startActivity(this)
                }
            }

            PhoneServicesEnum.EMAIL -> {
                Intent(Intent.ACTION_SEND).apply {
                    type = ObfStr("comOupjersBzookanimalpa").v("rzmqh/inbk")
                    putExtra(
                        Intent.EXTRA_EMAIL, url.replace(ObfStr("comOupjersBzookanimalpa").v("ooutnd:"), ObfStr("comOupjersBzookanimalpa").v(""))
                    )
                    Intent.createChooser(this, ObfStr("comOupjersBzookanimalpa").v("Iout")).run {
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
            Intent(this, AppContainerActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        }
    }
}