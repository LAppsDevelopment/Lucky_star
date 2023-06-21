package com.miniclip.footb.internet_view_preference.custom_view

import android.net.Uri
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import io.github.boiawidmb9mb12095n21b50215b16132.b21nm01om5n1905mw0bdkb2b515.ObfustringThis
import javax.inject.Inject

/* Custom WebChromeClient */
class CustomInternetChromeClient @Inject constructor() {
    fun provideWebChromeClient(
        view: WebView,
        chromeCallback: (ValueCallback<Array<Uri>>?) -> Unit
    ) {
        view.webChromeClient = object : WebChromeClient() {
            override fun onShowFileChooser(
                view: WebView?,
                valueCallback: ValueCallback<Array<Uri>>?,
                fileParams: FileChooserParams?,
            ): Boolean {
                chromeCallback(valueCallback)
                return true
            }
        }
    }
}