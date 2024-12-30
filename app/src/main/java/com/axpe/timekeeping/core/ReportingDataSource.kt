package com.axpe.timekeeping.core

import com.axpe.timekeeping.core.model.NetworkProject
import retrofit2.http.GET
import retrofit2.http.Path

interface ReportingDataSource {
    @GET("employee/projects/{userId}/{month}/{year}")
    suspend fun getProjects(
        @Path("userId") userId: Long,
        @Path("month") month: Int,
        @Path("year") year: Int,
    ): List<NetworkProject>
}