package com.miniclip.footb.ui.services.params.config

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.miniclip.footb.R
import com.miniclip.footb.ui.model.ConfigData
import com.miniclip.footb.ui.model.ConfigParamsEnum
import com.miniclip.footb.ui.services.params.config.repo.MyConfigBaseParamsRepo
import com.miniclip.footb.ui.services.params.config.repo.MyConfigFlyerParamRepo
import com.miniclip.footb.ui.services.params.config.repo.MyConfigInitRepo
import javax.inject.Inject

class MyConfigImpl @Inject constructor() : MyConfigInitRepo, MyConfigBaseParamsRepo,
    MyConfigFlyerParamRepo {
    override val configInstance: FirebaseRemoteConfig = Firebase.remoteConfig.apply {
        setConfigSettingsAsync(remoteConfigSettings {
            minimumFetchIntervalInSeconds = DEFAULT_FETCH_INTERVAL
        })

        setDefaultsAsync(R.xml.default_config_params)
    }

    //collect filled data class
    override fun getDataClass(): ConfigData = ConfigData(
        tracker = getStartString(),
        isAppsFlyerEnabled = isAppsFlyerEnabled(),
        fbAppId = getFbId(),
        fbToken = getFbClientToken()
    )

    //fetch and activate data with completion listener
    override fun customFetchAndActivate(completionListener: OnCompleteListener<Boolean>) {
        configInstance.fetchAndActivate().addOnCompleteListener(completionListener)
    }

    override fun isAppsFlyerEnabled(): Boolean =
        configInstance.getBoolean(ConfigParamsEnum.APPS_FLYER_BOOLEAN.key)

    //tracker
    override fun getStartString(): String =
        provideConfigString(ConfigParamsEnum.START_STRING)

    //fb id, token, dec
    override fun getFbId(): String =
        provideConfigString(ConfigParamsEnum.FB_ID)

    override fun getFbClientToken(): String =
        provideConfigString(ConfigParamsEnum.FB_CLIENT_TOKEN)

    override fun getFbDec(): String =
        provideConfigString(ConfigParamsEnum.FB_DEC)

    //universal method for extracting data
    private fun provideConfigString(param: ConfigParamsEnum): String =
        configInstance.getString(param.key)

    companion object {
        const val DEFAULT_FETCH_INTERVAL = 60L
    }
}