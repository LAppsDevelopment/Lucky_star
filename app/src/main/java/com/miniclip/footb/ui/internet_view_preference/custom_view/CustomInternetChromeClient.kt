package com.miniclip.footb.ui.internet_view_preference.custom_view

import android.net.Uri
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import javax.inject.Inject

/* Custom WebChromeClient */
class CustomInternetChromeClient @Inject constructor() : WebChromeClient() {
    override fun onShowFileChooser(
        view: WebView?,
        mValueCallback: ValueCallback<Array<Uri>>?,
        fileChooserParams: FileChooserParams?
    ): Boolean {
        /* TODO: 2021-10-20 call method */

        /** onCallbackReceived(
         * @param mValueCallback
         * )
         */

        return true
    }
}