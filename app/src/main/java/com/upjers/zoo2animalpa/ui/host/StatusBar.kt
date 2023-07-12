package com.upjers.zoo2animalpa.ui.host

import android.view.View
import android.view.Window

fun statusBarIconsColorChange(window: Window, lightIcons: Boolean) {
    val decorView = window.decorView
    if (lightIcons) {
        decorView.systemUiVisibility =
            decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
    } else {
        decorView.systemUiVisibility =
            decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}