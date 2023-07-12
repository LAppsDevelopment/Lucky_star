package com.upjers.zoo2animalpa.services.analytic

import android.content.Context
import android.content.Intent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun Context.checkAndNavigateWithValues(
    activityIntent: Intent? = null,
    isCache: Boolean = false,
    onFinish: (() -> Unit)? = null
) {
    CoroutineScope(SupervisorJob()).launch(Dispatchers.Main) {
        if (isCache) delay(1000)

        if (NotificationMessageManager.pushIntent != null) {
            startActivity(NotificationMessageManager.pushIntent)
            NotificationMessageManager.pushIntent = null
            onFinish?.invoke()
        } else {
            activityIntent?.let {
                startActivity(activityIntent)
                onFinish?.invoke()
            }
        }
    }
}