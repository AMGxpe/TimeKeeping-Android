package com.axpe.timekeeping.core.model

data class UserData(
    val username: String,
    val userId: Long,
    val isLogged: Boolean = false
) {
    companion object {
        fun notLogged() = UserData("", 0L, false)
    }
}
