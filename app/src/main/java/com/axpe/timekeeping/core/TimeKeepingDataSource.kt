package com.axpe.timekeeping.core

import com.axpe.timekeeping.core.model.NetworkLogin
import retrofit2.http.GET
import retrofit2.http.Header

interface TimeKeepingDataSource {
    @GET("perform_login")
    suspend fun doLogin(@Header("Authorization") authorization: String): NetworkLogin
}