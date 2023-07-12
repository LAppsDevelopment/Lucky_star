package com.upjers.zoo2animalpa.services.params.config.repo

import com.upjers.zoo2animalpa.model.ConfigData

interface MyConfigBaseParamsRepo {
    fun getStartString(): String

    fun getFbId(): String
    fun getFbClientToken(): String
    fun getFbDec(): String

    fun getDataClass(): ConfigData
}