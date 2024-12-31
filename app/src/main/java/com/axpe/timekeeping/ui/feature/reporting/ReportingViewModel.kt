package com.axpe.timekeeping.ui.feature.reporting

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.axpe.timekeeping.core.ReportingRepository
import com.axpe.timekeeping.core.model.NetworkConcept
import com.axpe.timekeeping.core.model.NetworkProject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.YearMonth
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ReportingViewModel @Inject constructor(
    private val reportingRepository: ReportingRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ReportingUiState())
    val uiState = _uiState.asStateFlow()
    val selectedProject = MutableStateFlow<NetworkProject?>(null)
    val selectedConcept = MutableStateFlow<NetworkConcept?>(null)

    init {
        _uiState.update { it.copy(projectsLoading = true) }
        viewModelScope.launch {
            reportingRepository.getProjects(YearMonth.now())
                .collect { value ->
                    _uiState.update { it.copy(projects = value, projectsLoading = false) }
                }
        }
        viewModelScope.launch {
            selectedProject.filterNotNull()
                .distinctUntilChanged()
                .flatMapLatest { selectedProject ->
                    _uiState.update { it.copy(conceptLoading = true, concepts = emptyList()) }
                    selectedConcept.value = null
                    reportingRepository
                        .getConcepts(YearMonth.now(), selectedProject.codProject)
                }
                .collectLatest { response ->
                    _uiState.update { it.copy(concepts = response, conceptLoading = false) }
                }
        }
    }

    fun selectProject(project: NetworkProject) {
        Log.d("AMG", "Cambiamos: $project")
        selectedProject.update {
            it?.copy(codProject = project.codProject, name = project.name) ?: project
        }
        Log.d("AMG", "Cambiamos: ${selectedProject.value}")
    }

    fun selectConcept(concept: NetworkConcept) {
        selectedConcept.value = concept
    }


    data class ReportingUiState(
        val projectsLoading: Boolean = false,
        val conceptLoading: Boolean = false,
        val projects: List<NetworkProject> = emptyList(),
        val concepts: List<NetworkConcept> = emptyList(),
    )
}