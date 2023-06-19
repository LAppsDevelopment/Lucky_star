package com.miniclip.footb.ui.services.params.long_awaited.m_referrer

import android.content.Context
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.boiawidmb9mb12095n21b50215b16132.b21nm01om5n1905mw0bdkb2b515.ObfustringThis
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@ObfustringThis
class MyReferrerImpl @Inject constructor(
    @ApplicationContext private val myAppContext: Context
) : MyReferrerRepo {
    override var clientInstance: InstallReferrerClient? = null

    override suspend fun getServiceString(): String? = suspendCoroutine { mContinuation ->
        var mReferrer: String? = null
        clientInstance = InstallReferrerClient.newBuilder(myAppContext).build()

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