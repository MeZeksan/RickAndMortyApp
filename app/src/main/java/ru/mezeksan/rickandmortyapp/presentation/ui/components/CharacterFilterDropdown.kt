package ru.mezeksan.rickandmortyapp.presentation.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import ru.mezeksan.rickandmortyapp.presentation.model.FilterOption
import ru.mezeksan.rickandmortyapp.ui.theme.PortalBlue
import ru.mezeksan.rickandmortyapp.ui.theme.PortalGreen
import ru.mezeksan.rickandmortyapp.ui.theme.ToxicText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterFilterDropdown(
    label: String,
    selected: FilterOption,
    options: List<FilterOption>,
    onOptionSelected: (FilterOption) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = stringResource(selected.labelResId),
            onValueChange = {},
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryNotEditable, enabled = true)
                .fillMaxWidth(),
            readOnly = true,
            singleLine = true,
            label = { Text(text = label, maxLines = 1) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = filterFieldColors(expanded),
            shape = MaterialTheme.shapes.medium
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.exposedDropdownSize(),
            containerColor = Color(0xFF1A2540)
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = stringResource(option.labelResId), color = ToxicText) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun filterFieldColors(expanded: Boolean) = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
    focusedBorderColor = if (expanded) PortalGreen.copy(alpha = 0.95f) else PortalBlue.copy(alpha = 0.7f),
    unfocusedBorderColor = PortalBlue.copy(alpha = 0.35f),
    focusedLabelColor = PortalGreen.copy(alpha = 0.95f),
    unfocusedLabelColor = PortalBlue.copy(alpha = 0.85f),
    focusedTextColor = ToxicText,
    unfocusedTextColor = ToxicText,
    unfocusedTrailingIconColor = PortalBlue.copy(alpha = 0.85f),
    focusedTrailingIconColor = PortalGreen
)
