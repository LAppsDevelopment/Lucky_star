package com.miniclip.footb.ui.web_screen

import android.os.Bundle
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

        setupWebView(binding.mWebView)

        binding.mWebView.loadUrl(getIntentWebUrl())
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
}