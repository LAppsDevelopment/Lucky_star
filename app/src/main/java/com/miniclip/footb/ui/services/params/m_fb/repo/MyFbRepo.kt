package com.miniclip.footb.ui.services.params.m_fb.repo

interface MyFbRepo {
    suspend fun getFetchedDeepLink(id: String?, token: String?): String?
}