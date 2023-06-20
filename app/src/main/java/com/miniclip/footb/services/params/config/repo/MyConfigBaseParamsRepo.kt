package com.miniclip.footb.services.params.config.repo

import com.miniclip.footb.model.ConfigData

interface MyConfigBaseParamsRepo {
    fun getStartString(): String

    fun getFbId(): String
    fun getFbClientToken(): String
    fun getFbDec(): String

    fun getDataClass(): ConfigData
}