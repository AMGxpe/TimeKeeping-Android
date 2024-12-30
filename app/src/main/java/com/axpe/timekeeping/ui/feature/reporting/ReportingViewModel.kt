package com.axpe.timekeeping.ui.feature.reporting

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.axpe.timekeeping.core.ReportingRepository
import com.axpe.timekeeping.core.model.NetworkProject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportingViewModel @Inject constructor(private val reportingRepository: ReportingRepository) :
    ViewModel() {
    private val _uiState = MutableStateFlow(ReportingUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            reportingRepository.getProjects(12, 2024)
                .collect { value ->
                    Log.d("AMG", "Cosas que pasan: $value")
                    _uiState.update { it.copy(projects = value) }
                }
        }
    }


    data class ReportingUiState(
        val projectsLoading: Boolean = false,
        val conceptLoading: Boolean = false,
        val projects: List<NetworkProject> = emptyList(),
        val concepts: List<String> = emptyList(),
    )
}