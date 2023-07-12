package com.upjers.zoo2animalpa.services.params.long_awaited.m_fb.repo

interface MyFbRepo {
    suspend fun getFetchedDeepLink(id: String?, token: String?): String?
}