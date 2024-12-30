package com.axpe.timekeeping.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkProject(
    @SerialName("cdproyecto")
    val codProject: Int,
    @SerialName("codproy_axpe")
    val name: String
)