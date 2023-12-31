package com.miniclip.footb.di

import android.app.Application
import com.miniclip.footb.services.analytic.NotificationMessageManager
import com.miniclip.footb.services.local_cache.MyDataStoreImpl
import com.miniclip.footb.services.signal_pusher.MySignalPusherImpl
import com.miniclip.footb.ui.web_screen.WorldWideWebActivity
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