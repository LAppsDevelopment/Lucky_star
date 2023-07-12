package com.upjers.zoo2animalpa.ui.intro_screen.interfaces

import kotlinx.coroutines.Job

interface RemoteServerScheme {
    fun remoteServerBuildProcess(): Job
    fun pathToWeb(appUrl: String?, isCache: Boolean)
    fun pathToLocalApp()
}