package com.miniclip.footb.services.extensions.phone_param.repo

interface PhoneExtensionRepo {
    val developmentSettings: String
    val defaultProperty: Int
    val fullChargedBattery: Float

    fun getChargeStatus(): Float
    fun isDeveloperSettingsEnable(): Boolean
    fun getAppPackageName(): String
}
