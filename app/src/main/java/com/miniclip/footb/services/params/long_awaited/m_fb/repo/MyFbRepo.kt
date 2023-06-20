package com.miniclip.footb.services.params.long_awaited.m_fb.repo

interface MyFbRepo {
    suspend fun getFetchedDeepLink(id: String?, token: String?): String?
}