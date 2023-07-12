package com.upjers.zoo2animalpa.services.extensions.g_param.repo

interface AdvertisingParamRepo {
    suspend fun getAdvertisingID(): String?
}
