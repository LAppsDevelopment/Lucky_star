package com.miniclip.footb.services.wide_preferences

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import android.webkit.ValueCallback
import android.webkit.WebSettings
import android.webkit.WebView
import com.miniclip.footb.back_callback.PageBackCallbackHandler
import com.miniclip.footb.internet_view_preference.custom_view.CustomInternetChromeClient
import com.miniclip.footb.internet_view_preference.custom_view.CustomInternetViewClient
import javax.inject.Inject

class WideWebPreferences @Inject constructor(
    private val pageBackCallbackHandler: PageBackCallbackHandler,
    private val customInternetViewClient: CustomInternetViewClient,
    private val chromeInternetClient: CustomInternetChromeClient
) : WideWebPreferencesInterface {

    override fun addBackCallback(webUrl: String, mView: WebView) {
        pageBackCallbackHandler.setCustomCallback(webUrl, mView)
    }

    override fun setupWebViewClient(mView: WebView) {
        mView.apply {
            webViewClient = customInternetViewClient
        }
    }

    override fun setupWebChromeClient(
        mView: WebView,
        myChromeCallback: (ValueCallback<Array<Uri>>?) -> Unit
    ) {
        chromeInternetClient.provideWebChromeClient(mView, myChromeCallback)
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