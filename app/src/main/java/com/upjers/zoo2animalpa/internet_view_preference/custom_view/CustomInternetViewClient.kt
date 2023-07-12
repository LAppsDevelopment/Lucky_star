package com.upjers.zoo2animalpa.internet_view_preference.custom_view

import io.github.boiawidmb9mb12095n21b50215b16132.b21nm01om5n1905mw0bdkb2b515.ObfStr
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.upjers.zoo2animalpa.internet_view_preference.custom_view.client_support.ClientIntentHandler
import com.upjers.zoo2animalpa.internet_view_preference.custom_view.client_support.extractWebViewActivity
import com.upjers.zoo2animalpa.internet_view_preference.custom_view.client_support.extractWebViewContext
import com.upjers.zoo2animalpa.model.PageUrlStartsEnum
import com.upjers.zoo2animalpa.model.PhoneServicesEnum
import io.github.boiawidmb9mb12095n21b50215b16132.b21nm01om5n1905mw0bdkb2b515.ObfustringThis
import javax.inject.Inject

/* Custom WebChromeClient */
@ObfustringThis
class CustomInternetViewClient @Inject constructor(
    private val intentHandler: ClientIntentHandler
) : WebViewClient() {
    private val mDefaultTitle = ObfStr("comOupjersBzookanimalpa").v("PfmvmxnrOwghuvds2221")

    override fun shouldOverrideUrlLoading(
        view: WebView?, request: WebResourceRequest?
    ): Boolean {
        val pageUrl = request?.url?.toString() ?: return false
        return try {
            val activityContext = extractWebViewContext(view)
            if (pageUrl.startsWith(PageUrlStartsEnum.TELEGRAM.startFrom)) {
                intentHandler.openPage(pageUrl, activityContext)
            }

            when {
                pageUrl.startsWith(PageUrlStartsEnum.HTTP.startFrom) || pageUrl.startsWith(
                    PageUrlStartsEnum.HTTPS.startFrom
                ) -> false

                else -> {
                    when {
                        pageUrl.startsWith(PhoneServicesEnum.EMAIL.value) -> {
                            intentHandler.openPhoneService(
                                service = PhoneServicesEnum.EMAIL,
                                url = pageUrl,
                                activityContext = activityContext
                            )
                        }

                        pageUrl.startsWith(PhoneServicesEnum.PHONE_CALL.value) -> {
                            intentHandler.openPhoneService(
                                service = PhoneServicesEnum.PHONE_CALL,
                                url = pageUrl,
                                activityContext = activityContext
                            )
                        }
                    }

                    intentHandler.openPage(pageUrl, activityContext)
                    true
                }
            }
        } catch (e: Exception) {
            true
        }
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        if (checkWebViewTitleAvailability(view)) {
            val mActivity = extractWebViewActivity(webView = view)

            intentHandler.runApplication(mActivity)
        }
    }

    // Check default title
    private fun checkWebViewTitleAvailability(webView: WebView?): Boolean {
        return webView?.title?.contains(mDefaultTitle) == true
    }
}