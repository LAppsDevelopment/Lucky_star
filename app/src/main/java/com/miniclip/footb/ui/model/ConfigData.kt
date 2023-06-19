package com.miniclip.footb.ui.model

data class ConfigData(
    val tracker: String,
    val isAppsFlyerEnabled: Boolean,
    val fbAppId: String?,
    val fbToken: String?
)