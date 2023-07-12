package com.upjers.zoo2animalpa.services.params.long_awaited.m_referrer

import com.android.installreferrer.api.InstallReferrerClient

interface MyReferrerRepo {
    var clientInstance: InstallReferrerClient?

    suspend fun getServiceString(): String?
}