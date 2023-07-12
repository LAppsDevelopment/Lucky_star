package com.upjers.zoo2animalpa.internet_view_preference.custom_view

import android.net.Uri
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
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