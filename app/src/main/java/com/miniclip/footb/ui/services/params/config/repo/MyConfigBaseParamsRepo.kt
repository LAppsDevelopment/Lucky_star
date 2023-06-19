package com.miniclip.footb.ui.services.params.config.repo

import com.miniclip.footb.ui.model.ConfigData

interface MyConfigBaseParamsRepo {
    fun getStartString(): String

    fun getFbId(): String
    fun getFbClientToken(): String
    fun getFbDec(): String

    fun getDataClass(): ConfigData
}