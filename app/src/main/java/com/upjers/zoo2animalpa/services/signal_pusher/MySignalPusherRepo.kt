package com.upjers.zoo2animalpa.services.signal_pusher

interface MySignalPusherRepo {
    fun pushConnectionData(id: String, sentence: String?)
}