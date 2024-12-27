package com.axpe.timekeeping.core

import com.axpe.timekeeping.core.model.NetworkLogin
import com.axpe.timekeeping.core.model.NetworkTimeKeeping
import java.time.Instant
import javax.inject.Inject
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
class TimeKeepingRepository @Inject constructor(private val timeKeepingDataSource: TimeKeepingDataSource) {
    suspend fun doLogin(username: String, password: String): NetworkLogin {
        val shieldedPassword = Base64.encode("$username//:$password".toByteArray())
        return timeKeepingDataSource.doLogin("Basic $shieldedPassword")
    }

    suspend fun sendTimeKeeping(userId: Long, day: Instant) =
        timeKeepingDataSource.sendTimeKeeping(userId, NetworkTimeKeeping.default(day))
}