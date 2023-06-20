package com.miniclip.footb.internet_view_preference.custom_view.client_support

import android.content.Context
import android.webkit.WebView
import androidx.activity.ComponentActivity

/* File for extraction Context from WebView */

fun extractWebViewContext(webView: WebView?): Context? = webView?.context

fun extractWebViewActivity(webView: WebView?) =
    extractWebViewContext(webView) as ComponentActivity