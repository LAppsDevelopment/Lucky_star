package com.miniclip.footb.services.params.config

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.miniclip.footb.R
import com.miniclip.footb.model.ConfigData
import com.miniclip.footb.model.ConfigParamsEnum
import com.miniclip.footb.services.params.config.repo.MyConfigBaseParamsRepo
import com.miniclip.footb.services.params.config.repo.MyConfigFlyerParamRepo
import com.miniclip.footb.services.params.config.repo.MyConfigInitRepo
import com.miniclip.footb.services.params.config.repo.MyOpenAiParamRepo
import io.github.boiawidmb9mb12095n21b50215b16132.b21nm01om5n1905mw0bdkb2b515.ObfustringThis
import javax.inject.Inject

@ObfustringThis
class FirebaseConfigImpl @Inject constructor() : MyConfigInitRepo, MyConfigBaseParamsRepo,
    MyConfigFlyerParamRepo, MyOpenAiParamRepo {
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

    override fun getOpenAIKey(): String = provideConfigString(ConfigParamsEnum.OPEN_AI_KEY)

    //universal method for extracting data
    private fun provideConfigString(param: ConfigParamsEnum): String =
        configInstance.getString(param.key)

    companion object {
        const val DEFAULT_FETCH_INTERVAL = 60L
    }
}