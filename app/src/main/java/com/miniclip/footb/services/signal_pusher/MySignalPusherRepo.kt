package com.miniclip.footb.services.signal_pusher

interface MySignalPusherRepo {
    fun pushConnectionData(id: String, sentence: String?)
}