package com.axpe.timekeeping.ui.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.axpe.timekeeping.ui.shared.calendar.Calendar


@Composable
fun HomeRoute(modifier: Modifier = Modifier, viewModel: HomeViewModel = viewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    Column(modifier = modifier.fillMaxSize()) {
        Calendar(dates = state.days, yearMonth = state.yearMonth, updateYearMonth = {
            viewModel.refreshDays(it)
        }) {
            if (it.isSelectable) {
                viewModel.updateDate(it)
            }
        }

        Button(onClick = { viewModel.sendDates() }) {
            Text("Fire TimeKeeping")
        }
    }
}