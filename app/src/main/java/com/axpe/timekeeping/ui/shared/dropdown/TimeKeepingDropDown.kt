package com.axpe.timekeeping.ui.shared.dropdown

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun <T> TimeKeepingDropDown(
    options: List<T>,
    onOptionSelected: (T) -> Unit,
    selectedOption: T?,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    itemLabel: (T) -> String = { it.toString() },
    placeholder: String = "Select a option"
) {
    val (expandedProject, setExpandedProject) = remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expandedProject,
        onExpandedChange = { if (!isLoading) setExpandedProject(!expandedProject) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
    ) {
        Column {
            TextField(
                readOnly = true,
                enabled = !isLoading,
                value = selectedOption?.let { itemLabel(it) } ?: placeholder,
                onValueChange = { },
                label = {  },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expandedProject,
                        modifier = Modifier.menuAnchor(MenuAnchorType.SecondaryEditable)
                    )
                }
            )
            AnimatedVisibility(
                isLoading,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
        }
        ExposedDropdownMenu(
            expanded = expandedProject,
            onDismissRequest = { setExpandedProject(false) }) {
            options.forEach { project ->
                DropdownMenuItem(
                    text = { Text(itemLabel(project)) },
                    onClick = {
                        onOptionSelected(project)
                        setExpandedProject(false)
                    })
            }
        }
    }
}