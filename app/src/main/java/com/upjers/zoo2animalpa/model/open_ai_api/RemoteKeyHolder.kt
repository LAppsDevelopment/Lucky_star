package com.upjers.zoo2animalpa.model.open_ai_api

import androidx.annotation.Keep


@Keep
class HolderManager {
    data class RemoteKeyHolder(
        var openAPIKey: String
    )

    val remoteKeyHolder = RemoteKeyHolder(openAPIKey = "")
}

