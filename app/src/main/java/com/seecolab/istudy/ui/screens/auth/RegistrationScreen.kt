package com.seecolab.istudy.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import com.seecolab.istudy.ui.viewmodel.RegistrationNavigationEvent
import com.seecolab.istudy.ui.viewmodel.RegistrationViewModel
import com.seecolab.istudy.ui.viewmodel.RegistrationUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    onNavigateToStudentHome: () -> Unit,
    onNavigateToParentHome: () -> Unit,
    onNavigateToLogin: () -> Unit,
    viewModel: RegistrationViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    
    // Handle navigation events
    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                is RegistrationNavigationEvent.NavigateToStudentHome -> onNavigateToStudentHome()
                is RegistrationNavigationEvent.NavigateToParentHome -> onNavigateToParentHome()
                is RegistrationNavigationEvent.NavigateToLogin -> onNavigateToLogin()
            }
        }
    }

    // Show message snackbar
    uiState.message?.let { message ->
        LaunchedEffect(message) {
            viewModel.clearMessage()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
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
                IconButton(onClick = { viewModel.navigateToLogin() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "返回",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(20.dp))

            // App Logo and Title
            Text(
                text = "iStudy",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 36.sp
                ),
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "家庭注册",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Registration Form Card
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
                        text = "家庭注册",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = "注册学生和主家长账户",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Student Information Section
                    StudentInformationSection(
                        uiState = uiState,
                        viewModel = viewModel
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Parent Information Section
                    ParentInformationSection(
                        uiState = uiState,
                        viewModel = viewModel
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Register Button
                    Button(
                        onClick = { viewModel.register() },
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
                                text = "注册",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Back to Login
                    TextButton(
                        onClick = { viewModel.navigateToLogin() },
                        enabled = !uiState.isLoading
                    ) {
                        Text(
                            text = "已有账户？立即登录",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StudentInformationSection(
    uiState: RegistrationUiState,
    viewModel: RegistrationViewModel
) {
    Column {
        Text(
            text = "学生信息",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Student Name
        OutlinedTextField(
            value = uiState.studentUsername,
            onValueChange = viewModel::updateStudentUsername,
            label = { Text("学生姓名") },
            enabled = !uiState.isLoading,
            isError = uiState.studentUsernameError != null,
            supportingText = uiState.studentUsernameError?.let { { Text(it) } },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Student Phone
        OutlinedTextField(
            value = uiState.studentTelephone,
            onValueChange = viewModel::updateStudentTelephone,
            label = { Text("学生手机号") },
            enabled = !uiState.isLoading,
            isError = uiState.studentTelephoneError != null,
            supportingText = uiState.studentTelephoneError?.let { { Text(it) } },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Student Age
        OutlinedTextField(
            value = uiState.studentAge,
            onValueChange = viewModel::updateStudentAge,
            label = { Text("学生年龄") },
            enabled = !uiState.isLoading,
            isError = uiState.studentAgeError != null,
            supportingText = uiState.studentAgeError?.let { { Text(it) } },
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
                value = uiState.studentSex?.displayName ?: "",
                onValueChange = { },
                readOnly = true,
                label = { Text("性别") },
                enabled = !uiState.isLoading,
                isError = uiState.studentSexError != null,
                supportingText = uiState.studentSexError?.let { { Text(it) } },
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
                            viewModel.updateStudentSex(sex)
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
                value = uiState.studentGrade?.displayName ?: "",
                onValueChange = { },
                readOnly = true,
                label = { Text("年级") },
                enabled = !uiState.isLoading,
                isError = uiState.studentGradeError != null,
                supportingText = uiState.studentGradeError?.let { { Text(it) } },
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
                            viewModel.updateStudentGrade(grade)
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
            value = uiState.studentPassword,
            onValueChange = viewModel::updateStudentPassword,
            label = { Text("学生密码") },
            enabled = !uiState.isLoading,
            isError = uiState.studentPasswordError != null,
            supportingText = uiState.studentPasswordError?.let { { Text(it) } },
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
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ParentInformationSection(
    uiState: RegistrationUiState,
    viewModel: RegistrationViewModel
) {
    Column {
        Text(
            text = "主家长信息",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Parent Name
        OutlinedTextField(
            value = uiState.parentUsername,
            onValueChange = viewModel::updateParentUsername,
            label = { Text("家长姓名") },
            enabled = !uiState.isLoading,
            isError = uiState.parentUsernameError != null,
            supportingText = uiState.parentUsernameError?.let { { Text(it) } },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Parent Phone
        OutlinedTextField(
            value = uiState.parentTelephone,
            onValueChange = viewModel::updateParentTelephone,
            label = { Text("家长手机号") },
            enabled = !uiState.isLoading,
            isError = uiState.parentTelephoneError != null,
            supportingText = uiState.parentTelephoneError?.let { { Text(it) } },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Parent Type Selection
        ExposedDropdownMenuBox(
            expanded = uiState.parentTypeDropdownExpanded,
            onExpandedChange = viewModel::updateParentTypeDropdownExpanded
        ) {
            OutlinedTextField(
                value = uiState.parentType?.displayName ?: "",
                onValueChange = { },
                readOnly = true,
                label = { Text("家长身份") },
                enabled = !uiState.isLoading,
                isError = uiState.parentTypeError != null,
                supportingText = uiState.parentTypeError?.let { { Text(it) } },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = uiState.parentTypeDropdownExpanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )
            
            ExposedDropdownMenu(
                expanded = uiState.parentTypeDropdownExpanded,
                onDismissRequest = { viewModel.updateParentTypeDropdownExpanded(false) }
            ) {
                listOf(UserTypeEnum.DAD, UserTypeEnum.MUM, UserTypeEnum.GRANDPARENTS).forEach { type ->
                    DropdownMenuItem(
                        text = { Text(type.displayName) },
                        onClick = {
                            viewModel.updateParentType(type)
                            viewModel.updateParentTypeDropdownExpanded(false)
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Parent Password
        var passwordVisible by remember { mutableStateOf(false) }
        OutlinedTextField(
            value = uiState.parentPassword,
            onValueChange = viewModel::updateParentPassword,
            label = { Text("家长密码") },
            enabled = !uiState.isLoading,
            isError = uiState.parentPasswordError != null,
            supportingText = uiState.parentPasswordError?.let { { Text(it) } },
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
    }
}