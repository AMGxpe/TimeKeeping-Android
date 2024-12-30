package com.axpe.timekeeping.core

import com.axpe.timekeeping.core.model.NetworkProject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReportingRepository @Inject constructor(
    private val reportingDataSource: ReportingDataSource,
    private val preferencesDataSource: PreferencesDataSource
) {
    suspend fun getProjects(month: Int, year: Int): Flow<List<NetworkProject>> =
        preferencesDataSource.getDataStoreUser().map { user ->
            reportingDataSource.getProjects(user.userId, month, year)
        }
}