package com.miniclip.footb.services.params.long_awaited.m_referrer

import com.android.installreferrer.api.InstallReferrerClient

interface MyReferrerRepo {
    var clientInstance: InstallReferrerClient?

    suspend fun getServiceString(): String?
}