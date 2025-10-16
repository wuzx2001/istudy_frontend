package com.seecolab.istudy.ui.screens.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.seecolab.istudy.data.model.*
import com.seecolab.istudy.ui.theme.IStudyTheme
import com.seecolab.istudy.ui.viewmodel.AddStudentNavigationEvent
import com.seecolab.istudy.ui.viewmodel.AddStudentViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddStudentScreen(
    onNavigateBack: () -> Unit,
    viewModel: AddStudentViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    
    // Handle navigation events
    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                is AddStudentNavigationEvent.NavigateBack -> onNavigateBack()
            }
        }
    }

    // Show message snackbar
    uiState.message?.let { message ->
        LaunchedEffect(message) {
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Short
            )
            viewModel.clearMessage()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                            MaterialTheme.colorScheme.background
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Top Bar with Back Button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { viewModel.navigateBack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "返回",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Title
                Text(
                    text = "添加学生",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp
                    ),
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = "填写学生信息",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Add Student Form Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "学生信息",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.SemiBold
                            ),
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        // Student Name
                        OutlinedTextField(
                            value = uiState.username,
                            onValueChange = viewModel::updateUsername,
                            label = { Text("学生姓名") },
                            enabled = !uiState.isLoading,
                            isError = uiState.usernameError != null,
                            supportingText = uiState.usernameError?.let { { Text(it) } },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Student Phone
                        OutlinedTextField(
                            value = uiState.telephone,
                            onValueChange = viewModel::updateTelephone,
                            label = { Text("学生手机号") },
                            enabled = !uiState.isLoading,
                            isError = uiState.telephoneError != null,
                            supportingText = uiState.telephoneError?.let { { Text(it) } },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Student Age
                        OutlinedTextField(
                            value = uiState.age,
                            onValueChange = viewModel::updateAge,
                            label = { Text("学生年龄") },
                            enabled = !uiState.isLoading,
                            isError = uiState.ageError != null,
                            supportingText = uiState.ageError?.let { { Text(it) } },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Student Sex Selection
                        ExposedDropdownMenuBox(
                            expanded = uiState.sexDropdownExpanded,
                            onExpandedChange = viewModel::updateSexDropdownExpanded
                        ) {
                            OutlinedTextField(
                                value = uiState.sex?.displayName ?: "",
                                onValueChange = { },
                                readOnly = true,
                                label = { Text("性别") },
                                enabled = !uiState.isLoading,
                                isError = uiState.sexError != null,
                                supportingText = uiState.sexError?.let { { Text(it) } },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = uiState.sexDropdownExpanded) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor()
                            )
                            
                            ExposedDropdownMenu(
                                expanded = uiState.sexDropdownExpanded,
                                onDismissRequest = { viewModel.updateSexDropdownExpanded(false) }
                            ) {
                                SexEnum.values().forEach { sex ->
                                    DropdownMenuItem(
                                        text = { Text(sex.displayName) },
                                        onClick = {
                                            viewModel.updateSex(sex)
                                            viewModel.updateSexDropdownExpanded(false)
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Student Grade Selection
                        ExposedDropdownMenuBox(
                            expanded = uiState.gradeDropdownExpanded,
                            onExpandedChange = viewModel::updateGradeDropdownExpanded
                        ) {
                            OutlinedTextField(
                                value = uiState.grade?.displayName ?: "",
                                onValueChange = { },
                                readOnly = true,
                                label = { Text("年级") },
                                enabled = !uiState.isLoading,
                                isError = uiState.gradeError != null,
                                supportingText = uiState.gradeError?.let { { Text(it) } },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = uiState.gradeDropdownExpanded) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor()
                            )
                            
                            ExposedDropdownMenu(
                                expanded = uiState.gradeDropdownExpanded,
                                onDismissRequest = { viewModel.updateGradeDropdownExpanded(false) }
                            ) {
                                GradeEnum.values().forEach { grade ->
                                    DropdownMenuItem(
                                        text = { Text(grade.displayName) },
                                        onClick = {
                                            viewModel.updateGrade(grade)
                                            viewModel.updateGradeDropdownExpanded(false)
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Student Password
                        var passwordVisible by remember { mutableStateOf(false) }
                        OutlinedTextField(
                            value = uiState.password,
                            onValueChange = viewModel::updatePassword,
                            label = { Text("学生密码") },
                            enabled = !uiState.isLoading,
                            isError = uiState.passwordError != null,
                            supportingText = uiState.passwordError?.let { { Text(it) } },
                            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                    Icon(
                                        imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                        contentDescription = if (passwordVisible) "隐藏密码" else "显示密码"
                                    )
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        // Add Student Button
                        Button(
                            onClick = { viewModel.addStudent() },
                            enabled = !uiState.isLoading && uiState.isFormValid,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            if (uiState.isLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            } else {
                                Text(
                                    text = "添加学生",
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontWeight = FontWeight.SemiBold
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Cancel Button
                        TextButton(
                            onClick = { viewModel.navigateBack() },
                            enabled = !uiState.isLoading
                        ) {
                            Text(
                                text = "取消",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}