package com.axpe.timekeeping.core

import com.axpe.timekeeping.core.model.NetworkLogin
import com.axpe.timekeeping.core.model.NetworkTimeKeeping
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface TimeKeepingDataSource {
    @GET("perform_login")
    suspend fun doLogin(@Header("Authorization") authorization: String): NetworkLogin

    @POST("v1/hora/{userId}")
    suspend fun sendTimeKeeping(
        @Path("userId") userId: Long,
        @Body body: NetworkTimeKeeping
    ): Response<Unit>
}