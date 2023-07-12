package com.upjers.zoo2animalpa.model.open_ai_api


class HolderManager {
    data class RemoteKeyHolder(
        var openAPIKey: String
    )

    val remoteKeyHolder = RemoteKeyHolder(openAPIKey = "")
}

