package com.upjers.zoo2animalpa.di

import android.app.Application
import com.upjers.zoo2animalpa.services.analytic.NotificationMessageManager
import com.upjers.zoo2animalpa.services.local_cache.MyDataStoreImpl
import com.upjers.zoo2animalpa.services.signal_pusher.MySignalPusherImpl
import com.upjers.zoo2animalpa.ui.web_screen.WorldWideWebActivity
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application() {

    @Inject
    lateinit var oneSignalClient: MySignalPusherImpl

    @Inject
    lateinit var dataStoreImpl: MyDataStoreImpl

    override fun onCreate() {
        super.onCreate()

        oneSignalClient.performServiceInit()
        NotificationMessageManager.setup<WorldWideWebActivity>(this, dataStore = dataStoreImpl)

    }

}