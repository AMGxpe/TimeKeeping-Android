package com.axpe.timekeeping.ui.feature.reporting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.axpe.timekeeping.R
import com.axpe.timekeeping.ui.shared.calendar.Calendar
import com.axpe.timekeeping.ui.shared.calendar.CalendarHeader
import com.axpe.timekeeping.ui.shared.dropdown.TimeKeepingDropDown
import com.axpe.timekeeping.ui.shared.loading.AnimationType
import com.axpe.timekeeping.ui.shared.loading.LoadingButton

@Composable
fun ReportingRoute(modifier: Modifier = Modifier, viewModel: ReportingViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val selectedProject by viewModel.selectedProject.collectAsStateWithLifecycle()
    val selectedConcept by viewModel.selectedConcept.collectAsStateWithLifecycle()
    val chooserYearMonths = List(3) { state.yearMonth.plusMonths(it.toLong() - 1) }
    Column(modifier = modifier.fillMaxSize()) {
        CalendarHeader(
            yearMonth = state.yearMonth,
            updateYearMonth = {
                viewModel.selectYearMonth(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)

        )
        TimeKeepingDropDown(
            options = state.projects,
            onOptionSelected = { viewModel.selectProject(it) },
            selectedOption = selectedProject,
            isLoading = state.projectsLoading,
            itemLabel = { it.name },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp),
            placeholder = stringResource(R.string.select_project)
        )
        TimeKeepingDropDown(
            options = state.concepts,
            onOptionSelected = { viewModel.selectConcept(it) },
            selectedOption = selectedConcept,
            isLoading = state.conceptLoading,
            itemLabel = { it.desctiption },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp),
            placeholder = stringResource(R.string.select_concept)
        )
        Calendar(
            dates = state.days,

            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 32.dp)
        ) {
            if (it.isSelectable) {
                viewModel.updateDate(it)
            }
        }

        LoadingButton(
            loading = state.reportingLoading,
            enabled = !state.reportingLoading,
            animationType = AnimationType.Fade,
            onClick = { viewModel.sendReporting() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 32.dp)
        ) {
            Text(stringResource(R.string.fire_timekeeping))
        }
    }
}