package com.axpe.timekeeping.ui.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.axpe.timekeeping.R
import com.axpe.timekeeping.ui.shared.calendar.Calendar
import com.axpe.timekeeping.ui.shared.loading.AnimationType
import com.axpe.timekeeping.ui.shared.loading.LoadingButton


@Composable
fun HomeRoute(modifier: Modifier = Modifier, viewModel: HomeViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        state.user?.let {
            Text(
                it.username,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
        }
        Calendar(dates = state.days, yearMonth = state.yearMonth, updateYearMonth = {
            viewModel.refreshDays(it)
        }) {
            if (it.isSelectable) {
                viewModel.updateDate(it)
            }
        }

        LoadingButton(
            modifier = Modifier.fillMaxWidth(0.7F),
            onClick = { viewModel.sendDates() },
            enabled = !state.isLoading,
            loading = state.isLoading,
            animationType = AnimationType.Fade
        ) {
            Text(stringResource(R.string.fire_timekeeping))
        }
    }
}