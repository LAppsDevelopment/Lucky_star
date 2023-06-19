package com.miniclip.footb.ui.services.extensions.phone_param

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.provider.Settings
import com.miniclip.footb.ui.services.extensions.phone_param.repo.PhoneExtensionRepo
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.boiawidmb9mb12095n21b50215b16132.b21nm01om5n1905mw0bdkb2b515.ObfustringThis
import javax.inject.Inject

/* Include params like dev, battery, packageName*/
@ObfustringThis
class PhoneExtensionImpl @Inject constructor(
    @ApplicationContext private val appContext: Context
) : PhoneExtensionRepo {
    override val developmentSettings = Settings.Global.DEVELOPMENT_SETTINGS_ENABLED
    override val defaultProperty = -1
    override val fullChargedBattery = 100f

    //developer check
    override fun isDeveloperSettingsEnable() = try {
        val defaultDeveloper = 0

        Settings.Global
            .getInt(
                appContext.contentResolver,
                developmentSettings,
                defaultDeveloper
            ) != defaultDeveloper
    } catch (e: Exception) {
        true
    }

    //package name
    override fun getAppPackageName(): String = appContext.packageName

    //battery percentage
    override fun getChargeStatus(): Float = try {
        appContext.registerReceiver(
            null,
            IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        ).let { intent ->
            intent?.extractIntentBattery() ?: fullChargedBattery
        }
    } catch (e: Exception) {
        fullChargedBattery
    }

    //count battery level
    private fun Intent.extractIntentBattery(): Float {
        return provideBatteryManagerProperties(BatteryManager.EXTRA_LEVEL) *
                fullChargedBattery /
                provideBatteryManagerProperties(
                    BatteryManager.EXTRA_SCALE
                )
    }

    //intent extra data extraction
    private fun Intent.provideBatteryManagerProperties(property: String) =
        getIntExtra(property, defaultProperty)
}