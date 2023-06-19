package com.miniclip.footb.ui.services.params.long_awaited.m_fb.repo

interface MyFbRepo {
    suspend fun getFetchedDeepLink(id: String?, token: String?): String?
}