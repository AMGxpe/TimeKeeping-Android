package com.axpe.timekeeping.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkConcept(
    @SerialName("cdconcepto")
    val cdConcepto: Int,
    @SerialName("dsconcepto")
    val desctiption: String
)