package com.miniclip.footb.ui.services.extensions.g_param.repo

interface AdvertisingParamRepo {
    suspend fun getAdvertisingID(): String?
}
