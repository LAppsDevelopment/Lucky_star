package com.miniclip.footb.ui.services.params.m_referrer

import android.content.Context
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MyReferrerImpl @Inject constructor(
    @ApplicationContext myAppContext: Context
) : MyReferrerRepo {
    override var clientInstance: InstallReferrerClient? = null

    init {
        clientInstance = InstallReferrerClient.newBuilder(myAppContext).build()
    }

    override suspend fun getServiceString(): String? = suspendCoroutine { mContinuation ->
        var mReferrer: String? = null

        clientInstance?.startConnection(object : InstallReferrerStateListener {
            override fun onInstallReferrerSetupFinished(executionCode: Int) {
                when (executionCode) {
                    InstallReferrerClient.InstallReferrerResponse.OK -> {
                        try {
                            mReferrer = provideDetails()?.installReferrer

                            mContinuation.resume(mReferrer)
                        } catch (e: Exception) {
                            mContinuation.resume(mReferrer)
                        }
                    }

                    else -> {
                        mContinuation.resume(mReferrer)
                    }
                }
                clientInstance?.endConnection()
            }

            override fun onInstallReferrerServiceDisconnected() {
                mContinuation.resume(mReferrer)
            }
        })
    }

    private fun provideDetails() = clientInstance?.installReferrer
}