package com.miniclip.footb.services.params.config.repo

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig

interface MyConfigInitRepo {
    val configInstance: FirebaseRemoteConfig

    fun customFetchAndActivate(completionListener: OnCompleteListener<Boolean>)
}