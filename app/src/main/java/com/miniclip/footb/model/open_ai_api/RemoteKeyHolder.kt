package com.miniclip.footb.model.open_ai_api

class HolderManager {
    data class RemoteKeyHolder(
        var openAPIKey: String
    )

    // Default value
    val remoteKeyHolder =
        RemoteKeyHolder(openAPIKey = "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")
}

