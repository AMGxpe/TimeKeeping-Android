package com.axpe.timekeeping.ui.feature.reporting

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
        updateProjects()
        viewModelScope.launch {
            selectedProject.filterNotNull()
                .distinctUntilChanged()
                .flatMapLatest { selectedProject ->
                    _uiState.update { it.copy(conceptLoading = true, concepts = emptyList()) }
                    selectedConcept.value = null
                    reportingRepository.getConcepts(
                        yearMonth = uiState.value.yearMonth,
                        codProject = selectedProject.codProject
                    )
                }
                .collectLatest { response ->
                    _uiState.update { it.copy(concepts = response, conceptLoading = false) }
                }
        }
    }

    fun selectProject(project: NetworkProject) {
        selectedProject.update {
            it?.copy(codProject = project.codProject, name = project.name) ?: project
        }
    }

    fun selectConcept(concept: NetworkConcept) {
        selectedConcept.value = concept
    }

    fun selectYearMonth(yearMonth: YearMonth) {
        _uiState.update { it.copy(yearMonth = yearMonth) }
        updateProjects()
    }

    private fun updateProjects() {
        _uiState.update { it.copy(projectsLoading = true) }
        viewModelScope.launch {
            reportingRepository.getProjects(uiState.value.yearMonth)
                .collect { value ->
                    _uiState.update { it.copy(projects = value, projectsLoading = false) }
                }
        }
    }


    data class ReportingUiState(
        val yearMonth: YearMonth = YearMonth.now(),
        val projectsLoading: Boolean = false,
        val conceptLoading: Boolean = false,
        val projects: List<NetworkProject> = emptyList(),
        val concepts: List<NetworkConcept> = emptyList(),
    )
}