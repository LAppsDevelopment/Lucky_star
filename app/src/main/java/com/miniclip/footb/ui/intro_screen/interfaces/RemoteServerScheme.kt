package com.miniclip.footb.ui.intro_screen.interfaces

import kotlinx.coroutines.Job

interface RemoteServerScheme {
    fun remoteServerBuildProcess(): Job
    fun pathToWeb(appUrl: String?, isCache: Boolean)
    fun pathToLocalApp()
}