package com.miniclip.footb.services.extensions.g_param.repo

interface AdvertisingParamRepo {
    suspend fun getAdvertisingID(): String?
}
