package com.upjers.zoo2animalpa.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ConfigData(
    @SerializedName(DOMAIN)
    val tracker: String,
    @SerializedName(IS_APPS_FLYER_ENABLED)
    val isAppsFlyerEnabled: Boolean,
    @SerializedName(FB_APP_ID)
    val fbAppId: String?,
    @SerializedName(FB_AT)
    val fbToken: String?
) {
    companion object {
        const val DOMAIN = "domain"
        const val IS_APPS_FLYER_ENABLED = "is_apps_flyer_enabled"
        const val FB_APP_ID = "fb_app_id"
        const val FB_AT = "fb_at"
    }
}