package com.seecolab.istudy.ui.screens.student

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.seecolab.istudy.R
import com.seecolab.istudy.data.model.Gender
import com.seecolab.istudy.data.model.Grade
import com.seecolab.istudy.ui.viewmodel.StudentRegistrationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentRegistrationScreen(
    onRegistrationComplete: () -> Unit,
    viewModel: StudentRegistrationViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isRegistrationComplete) {
        if (uiState.isRegistrationComplete) {
            onRegistrationComplete()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.student_registration),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = uiState.name,
            onValueChange = viewModel::updateName,
            label = { Text(stringResource(R.string.student_name)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            isError = uiState.nameError != null
        )
        
        uiState.nameError?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        OutlinedTextField(
            value = uiState.age,
            onValueChange = viewModel::updateAge,
            label = { Text(stringResource(R.string.student_age)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            isError = uiState.ageError != null
        )
        
        uiState.ageError?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        // Gender Selection
        Text(
            text = stringResource(R.string.student_gender),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .selectableGroup()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(
                modifier = Modifier
                    .selectable(
                        selected = (uiState.gender == Gender.MALE),
                        onClick = { viewModel.updateGender(Gender.MALE) },
                        role = Role.RadioButton
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (uiState.gender == Gender.MALE),
                    onClick = null
                )
                Text(
                    text = stringResource(R.string.male),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            
            Row(
                modifier = Modifier
                    .selectable(
                        selected = (uiState.gender == Gender.FEMALE),
                        onClick = { viewModel.updateGender(Gender.FEMALE) },
                        role = Role.RadioButton
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (uiState.gender == Gender.FEMALE),
                    onClick = null
                )
                Text(
                    text = stringResource(R.string.female),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        // Grade Selection
        var expanded by remember { mutableStateOf(false) }
        
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        ) {
            OutlinedTextField(
                value = uiState.grade?.displayName ?: "",
                onValueChange = {},
                readOnly = true,
                label = { Text(stringResource(R.string.student_grade)) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                isError = uiState.gradeError != null
            )
            
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                Grade.values().forEach { grade ->
                    DropdownMenuItem(
                        text = { Text(grade.displayName) },
                        onClick = {
                            viewModel.updateGrade(grade)
                            expanded = false
                        }
                    )
                }
            }
        }
        
        uiState.gradeError?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        Button(
            onClick = viewModel::registerStudent,
            modifier = Modifier.fillMaxWidth(),
            enabled = !uiState.isLoading
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text(stringResource(R.string.register))
            }
        }

        uiState.errorMessage?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}