package com.miniclip.footb.services.wide_preferences

import android.annotation.SuppressLint
import android.os.Build
import android.webkit.WebSettings
import android.webkit.WebView
import com.miniclip.footb.back_callback.PageBackCallbackHandler
import com.miniclip.footb.internet_view_preference.custom_view.CustomInternetChromeClient
import com.miniclip.footb.internet_view_preference.custom_view.CustomInternetViewClient
import javax.inject.Inject

class WideWebPreferences @Inject constructor(
    private val pageBackCallbackHandler: PageBackCallbackHandler,
    private val customInternetChromeClient: CustomInternetChromeClient,
    private val customInternetViewClient: CustomInternetViewClient
) : WideWebPreferencesInterface {

    override fun addBackCallback(webUrl: String, mView: WebView) {
        pageBackCallbackHandler.setCustomCallback(webUrl, mView)
    }

    override fun setupClients(mView: WebView) {
        mView.apply {
            webViewClient = customInternetViewClient
            webChromeClient = customInternetChromeClient
        }
    }

    override fun setupSettings(mViewSettings: WebSettings) {
        with(mViewSettings) {
            applyVisibleToUserSettings()

            applyFileContent()

            applyPorts()
            applyCacheSettings()
        }
    }

    private fun WebSettings.applyPorts() {
        userAgentString = this
            .userAgentString
            .replace("; wv", "")

        builtInZoomControls = true
        useWideViewPort = true

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O_MR1) {
            @Suppress("DEPRECATION")
            saveFormData = true
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun WebSettings.applyVisibleToUserSettings() {
        loadsImagesAutomatically = true
        javaScriptEnabled = true
        mixedContentMode = 0
        displayZoomControls = false
        javaScriptCanOpenWindowsAutomatically = true
        loadWithOverviewMode = true
    }

    private fun WebSettings.applyFileContent() {
        setSupportMultipleWindows(false)
        allowContentAccess = true
        allowFileAccess = true
    }

    private fun WebSettings.applyCacheSettings() {
        cacheMode = WebSettings.LOAD_DEFAULT
        databaseEnabled = true
        domStorageEnabled = true
    }
}