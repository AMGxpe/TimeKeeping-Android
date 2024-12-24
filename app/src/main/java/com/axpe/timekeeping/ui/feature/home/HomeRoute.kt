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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.axpe.timekeeping.core.model.UserData
import com.axpe.timekeeping.getDataStoreUser
import com.axpe.timekeeping.ui.shared.calendar.Calendar
import com.axpe.timekeeping.ui.shared.loading.AnimationType
import com.axpe.timekeeping.ui.shared.loading.LoadingButton


@Composable
fun HomeRoute(modifier: Modifier = Modifier, viewModel: HomeViewModel = viewModel()) {
    val context = LocalContext.current
    val user =
        context.getDataStoreUser().collectAsStateWithLifecycle(UserData.notLogged())
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            user.value.username,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        Calendar(dates = state.days, yearMonth = state.yearMonth, updateYearMonth = {
            viewModel.refreshDays(it)
        }) {
            if (it.isSelectable) {
                viewModel.updateDate(it)
            }
        }

        LoadingButton(
            modifier = Modifier.fillMaxWidth(0.7F),
            onClick = { viewModel.sendDates(user.value.userId) },
            enabled = !state.isLoading,
            loading = state.isLoading,
            animationType = AnimationType.Fade
        ) {
            Text("Fire TimeKeeping")
        }
    }
}