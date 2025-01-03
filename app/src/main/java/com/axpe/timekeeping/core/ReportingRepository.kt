package com.axpe.timekeeping.core

import com.axpe.timekeeping.core.model.NetworkConcept
import com.axpe.timekeeping.core.model.NetworkProject
import kotlinx.coroutines.flow.Flow
import java.time.YearMonth
import javax.inject.Inject

class ReportingRepository @Inject constructor(
    private val reportingDataSource: ReportingDataSource,
    private val preferencesDataSource: PreferencesDataSource
) {
    fun getProjects(yearMonth: YearMonth): Flow<List<NetworkProject>> =
        preferencesDataSource.withUserIdFlow { userId ->
            reportingDataSource.getProjects(userId, yearMonth.monthValue, yearMonth.year)
        }

    fun getConcepts(yearMonth: YearMonth, codProject: Int): Flow<List<NetworkConcept>> =
        preferencesDataSource.withUserIdFlow { userId ->
            reportingDataSource.getConceptsByProject(
                userId = userId,
                month = yearMonth.monthValue,
                year = yearMonth.year,
                codProject = codProject
            )
        }


}