package com.miniclip.footb.ui.web_screen

import android.os.Bundle
import android.webkit.CookieManager
import android.webkit.WebView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.miniclip.footb.R
import com.miniclip.footb.databinding.ActivityWorldWideWebBinding
import com.miniclip.footb.services.analytic.NotificationMessageManager.URL_KEY
import com.miniclip.footb.services.image_service.WorldWideWebImageServiceImpl
import com.miniclip.footb.services.wide_preferences.WideWebPreferences
import com.miniclip.footb.viewmodels.WorldWideWebViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WorldWideWebActivity : AppCompatActivity() {
    private var _binding: ActivityWorldWideWebBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var wideWebPreferences: WideWebPreferences

    @Inject
    lateinit var wideWebImageServiceImpl: WorldWideWebImageServiceImpl

    private val wwwViewModel: WorldWideWebViewModel by viewModels()

    private val requestPermissionOnImage =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            it?.let {
                wideWebImageServiceImpl.startCamera(it)
            }
        }
    private val imagePermissionResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            wideWebImageServiceImpl.createImageResult(it)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityWorldWideWebBinding.inflate(layoutInflater)
        setContentView(binding.root)

        launchCookieManager(true)

        setupWebView(binding.mWebView)

        binding.mWebView.loadUrl(getIntentWebUrl())

        loader()

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
            setupWebViewClient(mWebView)
            setupWebChromeClient(mWebView) { valueCallback ->
                wideWebImageServiceImpl.permissionsResultCallback(
                    valueCallback,
                    requestPermissionOnImage,
                    imagePermissionResult
                )
            }
            addBackCallback(getIntentWebUrl(), mWebView)
        }
    }

    private fun getIntentWebUrl(): String {
        val luckyLink = intent.getStringExtra(URL_KEY) ?: ""

        if (luckyLink.isNotBlank()) {
            wwwViewModel.saveUrlToDataStore(luckyLink)
        }

        return luckyLink
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

    private fun loader() {
        Toast(this@WorldWideWebActivity).apply {
            duration = Toast.LENGTH_LONG
            view = layoutInflater.inflate(
                R.layout.layout_loader,
                findViewById(R.id.loader_layout)
            )
        }.show()
    }
}