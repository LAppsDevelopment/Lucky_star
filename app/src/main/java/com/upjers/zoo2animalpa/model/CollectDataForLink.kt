package com.upjers.zoo2animalpa.model

import androidx.annotation.Keep

@Keep
data class CollectDataForLink(
    var appsFlyerMap: Map<String, String?>? = null,
    var referrer: String? = null,
    var deeplink: String? = null,
    var gaid: String? = null,
    var appsFlyerID: String? = null,
    var adb: Boolean = false,
    var bundle: String = "",
    var battery: Float = 0.0f,
)
