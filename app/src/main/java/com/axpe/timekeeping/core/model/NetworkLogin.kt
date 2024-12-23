package com.axpe.timekeeping.core.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkLogin(
    val user: String,
//    val pass: String,
    val fullName: String,
    val employee: Int,
//    val enterprise: Int,
//    val manager: String?,
//    val rol: Int,
//    val unit: String?,
    val token: String

)