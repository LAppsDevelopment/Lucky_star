package com.miniclip.footb.ui.services.params.m_referrer

import com.android.installreferrer.api.InstallReferrerClient

interface MyReferrerRepo {
    var clientInstance: InstallReferrerClient?

    suspend fun getServiceString(): String?
}