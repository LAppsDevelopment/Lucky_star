package com.miniclip.footb.back_callback

import android.webkit.WebView
import androidx.activity.OnBackPressedCallback
import com.miniclip.footb.internet_view_preference.custom_view.client_support.extractWebViewActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/* Custom onBackPressedDispatcher callback */
class PageBackCallbackHandler @Inject constructor() {
    private var backStepEnable = false
    private val valueChangeDelay = 2002L

    //TODO call from WebActivity
    fun setCustomCallback(basePageUrl: String, webView: WebView) {
        val mActivity = extractWebViewActivity(webView)

        mActivity.onBackPressedDispatcher.addCallback(
            mActivity,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    with(webView) {
                        if (canGoBack()) {
                            doLoadCheck(webView, basePageUrl)

                            backStepEnable = true

                            webView.goBack()
                            performDelay()
                        }
                    }
                }
            }
        )
    }

    /* Load base url if back step enabled */
    private fun doLoadCheck(webView: WebView, basePageUrl: String) {
        if (backStepEnable) {
            webView.loadUrl(basePageUrl)
        }
    }

    /* Change valueChangeDelay with custom delay */
    private fun performDelay() {
        CoroutineScope(Dispatchers.Main).launch {
            delay(valueChangeDelay)
            backStepEnable = false
        }
    }
}