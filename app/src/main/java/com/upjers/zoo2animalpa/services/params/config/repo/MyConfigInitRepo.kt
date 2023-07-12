package com.upjers.zoo2animalpa.services.params.config.repo

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig

interface MyConfigInitRepo {
    val configInstance: FirebaseRemoteConfig

    fun customFetchAndActivate(completionListener: OnCompleteListener<Boolean>)
}