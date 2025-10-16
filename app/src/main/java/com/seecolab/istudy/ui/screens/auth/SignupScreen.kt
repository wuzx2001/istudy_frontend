package com.seecolab.istudy.ui.screens.auth

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.seecolab.istudy.data.model.UserRole
import com.seecolab.istudy.ui.theme.IStudyTheme
import com.seecolab.istudy.ui.viewmodel.SignupNavigationEvent
import com.seecolab.istudy.ui.viewmodel.SignupViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(
    onNavigateToStudentHome: () -> Unit,
    onNavigateToParentHome: () -> Unit,
    onNavigateToLogin: () -> Unit,
    viewModel: SignupViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    
    // Handle navigation events
    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                is SignupNavigationEvent.NavigateToStudentHome -> onNavigateToStudentHome()
                is SignupNavigationEvent.NavigateToParentHome -> onNavigateToParentHome()
                is SignupNavigationEvent.NavigateToLogin -> onNavigateToLogin()
            }
        }
    }
    
    // Show message snackbar
    uiState.message?.let { message ->
        LaunchedEffect(message) {
            // Show snackbar here if needed
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
                text = "加入智能学习社区",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Signup Form Card
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
                        text = "创建账户",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "填写下方信息完成注册",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    // Role Selection
                    RoleSelectionSection(
                        selectedRole = uiState.selectedRole,
                        onRoleSelected = viewModel::updateRole
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Phone Number Input
                    PhoneNumberInput(
                        phoneNumber = uiState.phoneNumber,
                        onPhoneNumberChange = viewModel::updatePhoneNumber,
                        error = uiState.phoneError,
                        enabled = !uiState.isLoading
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Password Input
                    PasswordInput(
                        password = uiState.password,
                        onPasswordChange = viewModel::updatePassword,
                        error = uiState.passwordError,
                        enabled = !uiState.isLoading,
                        label = "密码"
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Confirm Password Input
                    PasswordInput(
                        password = uiState.confirmPassword,
                        onPasswordChange = viewModel::updateConfirmPassword,
                        error = uiState.confirmPasswordError,
                        enabled = !uiState.isLoading,
                        label = "确认密码"
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Verification Code Input
                    VerificationCodeInput(
                        code = uiState.verificationCode,
                        onCodeChange = viewModel::updateVerificationCode,
                        error = uiState.verificationError,
                        enabled = !uiState.isLoading,
                        isCodeSent = uiState.isCodeSent,
                        countdown = uiState.countdown,
                        onSendCode = viewModel::sendVerificationCode
                    )
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    // Signup Button
                    Button(
                        onClick = viewModel::signup,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        enabled = !uiState.isLoading,
                        shape = RoundedCornerShape(25.dp)
                    ) {
                        if (uiState.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        } else {
                            Text(
                                text = "注册",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Login Link
            TextButton(
                onClick = { viewModel.navigateToLogin() }
            ) {
                Text(
                    text = "已有账户？立即登录",
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RoleSelectionSection(
    selectedRole: UserRole,
    onRoleSelected: (UserRole) -> Unit
) {
    Column {
        Text(
            text = "我是",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            UserRole.values().forEach { role ->
                FilterChip(
                    onClick = { onRoleSelected(role) },
                    label = {
                        Text(
                            text = when (role) {
                                UserRole.STUDENT -> "学生"
                                UserRole.PARENT -> "家长"
                            }
                        )
                    },
                    selected = selectedRole == role,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun PhoneNumberInput(
    phoneNumber: String,
    onPhoneNumberChange: (String) -> Unit,
    error: String?,
    enabled: Boolean
) {
    Column {
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = onPhoneNumberChange,
            label = { Text("手机号") },
            placeholder = { Text("请输入手机号") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            isError = error != null,
            enabled = enabled,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        
        error?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}

@Composable
private fun PasswordInput(
    password: String,
    onPasswordChange: (String) -> Unit,
    error: String?,
    enabled: Boolean,
    label: String
) {
    var passwordVisible by remember { mutableStateOf(false) }
    
    Column {
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text(label) },
            placeholder = { Text("请输入$label") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = if (passwordVisible) "隐藏密码" else "显示密码"
                    )
                }
            },
            isError = error != null,
            enabled = enabled,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        
        error?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}

@Composable
private fun VerificationCodeInput(
    code: String,
    onCodeChange: (String) -> Unit,
    error: String?,
    enabled: Boolean,
    isCodeSent: Boolean,
    countdown: Int,
    onSendCode: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            OutlinedTextField(
                value = code,
                onValueChange = onCodeChange,
                label = { Text("验证码") },
                placeholder = { Text("请输入验证码") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = error != null,
                enabled = enabled,
                modifier = Modifier.weight(1f),
                singleLine = true
            )
            
            Button(
                onClick = onSendCode,
                enabled = enabled && countdown == 0,
                modifier = Modifier.height(56.dp)
            ) {
                Text(
                    text = when {
                        countdown > 0 -> "${countdown}秒"
                        isCodeSent -> "重发"
                        else -> "发送"
                    }
                )
            }
        }
        
        error?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SignupScreenPreview() {
    IStudyTheme {
        SignupScreen(
            onNavigateToStudentHome = {},
            onNavigateToParentHome = {},
            onNavigateToLogin = {}
        )
    }
}