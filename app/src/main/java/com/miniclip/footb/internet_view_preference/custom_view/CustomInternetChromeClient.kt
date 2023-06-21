package com.miniclip.footb.internet_view_preference.custom_view

import android.net.Uri
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView

/* Custom WebChromeClient */
class CustomInternetChromeClient(
    private val chromeCallback: (ValueCallback<Array<Uri>>?) -> Unit
) : WebChromeClient() {
    override fun onShowFileChooser(
        view: WebView?,
        mValueCallback: ValueCallback<Array<Uri>>?,
        fileChooserParams: FileChooserParams?
    ): Boolean {
        chromeCallback(mValueCallback)
        return true
    }

}