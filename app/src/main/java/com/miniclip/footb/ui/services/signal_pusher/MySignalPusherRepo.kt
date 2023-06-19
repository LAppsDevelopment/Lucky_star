package com.miniclip.footb.ui.services.signal_pusher

interface MySignalPusherRepo {
    fun pushConnectionData(id: String, sentence: String?)
}