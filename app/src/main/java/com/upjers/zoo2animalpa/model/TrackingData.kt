package com.upjers.zoo2animalpa.model

import com.google.gson.annotations.SerializedName
import com.upjers.zoo2animalpa.model.CommonValues.ADB_NAME
import com.upjers.zoo2animalpa.model.CommonValues.AF_USER_ID_NAME
import com.upjers.zoo2animalpa.model.CommonValues.BATTERY_NAME
import com.upjers.zoo2animalpa.model.CommonValues.BUNDLE_NAME
import com.upjers.zoo2animalpa.model.CommonValues.DECRYPTION_KEY_NAME
import com.upjers.zoo2animalpa.model.CommonValues.DEEPLINK_NAME
import com.upjers.zoo2animalpa.model.CommonValues.DEVELOPER_KEY_NAME
import com.upjers.zoo2animalpa.model.CommonValues.GOOGLE_AD_ID_NAME
import com.upjers.zoo2animalpa.model.CommonValues.IS_RANDOM_ENABLED_NAME
import com.upjers.zoo2animalpa.model.CommonValues.REFERRER_NAME

data class TrackingData(

    @SerializedName(BATTERY_NAME)
    val userBattery: String?,

    @SerializedName(DEVELOPER_KEY_NAME)
    val appDeveloperKey: String?,

    @SerializedName(BUNDLE_NAME)
    val applicationId: String?,

    @SerializedName(AF_USER_ID_NAME)
    val appsId: String?,

    @SerializedName(GOOGLE_AD_ID_NAME)
    val googleAdId: String?,

    @SerializedName(ADB_NAME)
    val isUserDeveloper: Boolean?,

    @SerializedName(IS_RANDOM_ENABLED_NAME)
    val randomParamsInLinkEnabled: Boolean = false,

    @SerializedName(DEEPLINK_NAME)
    val facebookDeeplink: String?,

    @SerializedName(REFERRER_NAME)
    val installReferrer: String?,

    @SerializedName(DECRYPTION_KEY_NAME)
    val facebookDecryption: String?,
    // Unchanged field
    val remoteConfig: ConfigData,
    // Unchanged field
    val attributionData: Map<String, String?>?

)
