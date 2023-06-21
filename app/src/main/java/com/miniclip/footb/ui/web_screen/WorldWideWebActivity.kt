package com.miniclip.footb.ui.web_screen

import android.os.Bundle
import android.webkit.CookieManager
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.miniclip.footb.databinding.ActivityWorldWideWebBinding
import com.miniclip.footb.services.analytic.NotificationMessageManager.URL_KEY
import com.miniclip.footb.services.wide_preferences.WideWebPreferences
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WorldWideWebActivity : AppCompatActivity() {
    private var _binding: ActivityWorldWideWebBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var wideWebPreferences: WideWebPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityWorldWideWebBinding.inflate(layoutInflater)
        setContentView(binding.root)

        launchCookieManager(true)

        setupWebView(binding.mWebView)

        binding.mWebView.loadUrl(getIntentWebUrl())
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        binding.mWebView.saveState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        binding.mWebView.restoreState(savedInstanceState)
    }

    override fun onPause() {
        launchCookieManager(false)
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

    private fun setupWebView(mWebView: WebView) {
        wideWebPreferences.apply {
            setupSettings(mWebView.settings)
            setupClients(mWebView)
            addBackCallback(getIntentWebUrl(), mWebView)
        }
    }

    private fun getIntentWebUrl(): String {
        return intent.getStringExtra(URL_KEY) ?: ""
    }

    private fun launchCookieManager(enable: Boolean) {
        CookieManager.getInstance().apply {
            if (enable) {
                setAcceptCookie(true)
                setAcceptThirdPartyCookies(binding.mWebView, true)
            } else {
                flush()
            }
        }
    }
}