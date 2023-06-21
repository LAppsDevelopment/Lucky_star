package com.miniclip.footb.services.wide_preferences

import android.webkit.WebSettings
import android.webkit.WebView

interface WideWebPreferencesInterface {
    fun addBackCallback(webUrl: String, mView: WebView)
    fun setupClients(mView: WebView)
    fun setupSettings(mViewSettings: WebSettings)
}