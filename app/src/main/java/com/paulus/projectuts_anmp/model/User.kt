package com.paulus.projectuts_anmp.model

data class User (
    val id: Int,
    val username:String?,
    val first_name:String?,
    val last_name:String?,
    val email:String?,
    val password:String?
)