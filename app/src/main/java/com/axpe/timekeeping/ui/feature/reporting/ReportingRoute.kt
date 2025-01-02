package com.axpe.timekeeping.ui.feature.reporting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.axpe.timekeeping.R
import com.axpe.timekeeping.ui.shared.calendar.Calendar
import com.axpe.timekeeping.ui.shared.dropdown.TimeKeepingDropDown
import java.time.format.DateTimeFormatter

@Composable
fun ReportingRoute(modifier: Modifier = Modifier, viewModel: ReportingViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val selectedProject by viewModel.selectedProject.collectAsStateWithLifecycle()
    val selectedConcept by viewModel.selectedConcept.collectAsStateWithLifecycle()
    val chooserYearMonths = List(3) { state.yearMonth.plusMonths(it.toLong() - 1) }
    Column(modifier = modifier.fillMaxSize()) {
        TimeKeepingDropDown(
            options = chooserYearMonths,
            onOptionSelected = { viewModel.selectYearMonth(it) },
            selectedOption = state.yearMonth,
            isLoading = false,
            itemLabel = { it.format(DateTimeFormatter.ofPattern("MMMM - yyyy")) }
        )
        TimeKeepingDropDown(
            options = state.projects,
            onOptionSelected = { viewModel.selectProject(it) },
            selectedOption = selectedProject,
            isLoading = state.projectsLoading,
            itemLabel = { it.name },
            placeholder = stringResource(R.string.select_project)
        )
        TimeKeepingDropDown(
            options = state.concepts,
            onOptionSelected = { viewModel.selectConcept(it) },
            selectedOption = selectedConcept,
            isLoading = state.conceptLoading,
            itemLabel = { it.desctiption },
            placeholder = stringResource(R.string.select_concept)
        )
    }
}