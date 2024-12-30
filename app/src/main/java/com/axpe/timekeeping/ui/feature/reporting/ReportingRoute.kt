package com.axpe.timekeeping.ui.feature.reporting

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ReportingRoute(modifier: Modifier = Modifier, viewModel: ReportingViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ReportingScreen(modifier)

}

@Composable
fun ReportingScreen(modifier: Modifier = Modifier) {
    Text(text = "Reporting", modifier = modifier)
}