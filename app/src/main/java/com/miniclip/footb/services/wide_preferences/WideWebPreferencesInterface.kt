package com.miniclip.footb.services.wide_preferences

import android.net.Uri
import android.webkit.ValueCallback
import android.webkit.WebSettings
import android.webkit.WebView

interface WideWebPreferencesInterface {
    fun addBackCallback(webUrl: String, mView: WebView)
    fun setupWebViewClient(mView: WebView)
    fun setupWebChromeClient(mView: WebView, myChromeCallback: (ValueCallback<Array<Uri>>?) -> Unit)
    fun setupSettings(mViewSettings: WebSettings)
}