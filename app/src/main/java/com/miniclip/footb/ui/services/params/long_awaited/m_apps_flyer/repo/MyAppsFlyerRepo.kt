package com.miniclip.footb.ui.services.params.long_awaited.m_apps_flyer.repo

import com.appsflyer.AppsFlyerConversionListener
import kotlin.coroutines.Continuation

interface MyAppsFlyerRepo {
    val myConversionListener: (Continuation<Map<String, String?>?>) -> AppsFlyerConversionListener

    suspend fun getConversionMap(): Map<String, String?>?
    fun getServiceUID(): String?
}