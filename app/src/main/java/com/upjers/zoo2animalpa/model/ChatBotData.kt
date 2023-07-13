package com.upjers.zoo2animalpa.model

import androidx.annotation.Keep

@Keep
data class ChatBotData(
    var userQuery: String = "Query is empty",
)