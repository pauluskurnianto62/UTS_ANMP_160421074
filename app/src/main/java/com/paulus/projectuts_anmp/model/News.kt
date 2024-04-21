package com.paulus.projectuts_anmp.model

data class News (
    val id: Int,
    val title: String?,
    val author:String?,
    val short_desc: String?,
    val detail: Article?,
    val images: String?
)

data class Article (
    val article: List<String>?
)