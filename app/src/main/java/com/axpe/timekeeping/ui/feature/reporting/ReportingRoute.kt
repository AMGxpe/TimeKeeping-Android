package com.axpe.timekeeping.ui.feature.reporting

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportingRoute(modifier: Modifier = Modifier, viewModel: ReportingViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val selectedProject by viewModel.selectedProject.collectAsStateWithLifecycle()
    val selectedConcept by viewModel.selectedConcept.collectAsStateWithLifecycle()
    Column(modifier = Modifier.fillMaxSize()) {

        val (textProject, setTextProject) = remember { mutableStateOf("") }
        val (expandedProject, setExpandedProject) = remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(
            expanded = expandedProject,
            onExpandedChange = { if (!state.projectsLoading) setExpandedProject(!expandedProject) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        ) {
            Column {
                TextField(
                    readOnly = true,
                    enabled = !state.projectsLoading,
                    value = selectedProject?.name ?: "Select a project",
                    onValueChange = { setTextProject(it) },
                    label = { Text(textProject) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(
                            MenuAnchorType.PrimaryNotEditable
                        ),
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expandedProject,
                            modifier = Modifier.menuAnchor(MenuAnchorType.SecondaryEditable)
                        )
                    }
                )
                AnimatedVisibility(
                    state.projectsLoading,
                    enter = expandVertically(),
                    exit = shrinkVertically()
                ) {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                }
            }
            ExposedDropdownMenu(
                expanded = expandedProject,
                onDismissRequest = { setExpandedProject(false) }) {
                state.projects.forEach { project ->
                    DropdownMenuItem(
                        text = { Text(project.name) },
                        onClick = {
                            viewModel.selectProject(project)
                            setExpandedProject(false)
                        })
                }
            }
        }
        val (textConcept, setTextConcept) = remember { mutableStateOf("") }
        val (expandedConcept, setExpandedConcept) = remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(
            expanded = expandedConcept,
            onExpandedChange = { setExpandedConcept(!expandedConcept) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        ) {
            Column {
                TextField(
                    readOnly = true,
                    value = selectedConcept?.desctiption ?: "Select a concept",
                    onValueChange = { setTextConcept(it) },
                    label = { Text(textConcept) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(
                            MenuAnchorType.PrimaryNotEditable
                        ),
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expandedConcept,
                            modifier = Modifier.menuAnchor(MenuAnchorType.SecondaryEditable)
                        )
                    }
                )
                AnimatedVisibility(
                    visible = state.conceptLoading,
                    enter = expandVertically(),
                    exit = shrinkVertically()
                ) {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                }
            }
            ExposedDropdownMenu(
                expanded = expandedConcept,
                onDismissRequest = { setExpandedConcept(false) }) {
                state.concepts.forEach { concept ->
                    DropdownMenuItem(
                        text = { Text(concept.desctiption) },
                        onClick = {
                            viewModel.selectConcept(concept)
                            setExpandedConcept(false)
                        })
                }
            }
        }
    }
}