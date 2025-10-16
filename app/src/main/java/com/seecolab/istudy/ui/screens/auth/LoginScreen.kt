package com.seecolab.istudy.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
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
import com.seecolab.istudy.R
import com.seecolab.istudy.data.model.LoginType
import com.seecolab.istudy.ui.theme.IStudyTheme
import com.seecolab.istudy.ui.viewmodel.LoginNavigationEvent
import com.seecolab.istudy.ui.viewmodel.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onNavigateToStudentHome: () -> Unit,
    onNavigateToParentHome: () -> Unit,
    onNavigateToSignup: () -> Unit,
    onNavigateToRegistration: () -> Unit,
    onNavigateToTeacherRegistration: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    
    // Handle navigation events
    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                is LoginNavigationEvent.NavigateToStudentHome -> onNavigateToStudentHome()
                is LoginNavigationEvent.NavigateToParentHome -> onNavigateToParentHome()
                is LoginNavigationEvent.NavigateToRegistration -> onNavigateToRegistration()
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
            Spacer(modifier = Modifier.height(60.dp))
            
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
                text = stringResource(R.string.app_subtitle),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )
            
            Spacer(modifier = Modifier.height(48.dp))
            
            // Login Form Card
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
                        text = "欢迎回来",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "请输入您的登录信息",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    // Phone Number Input
                    PhoneNumberInput(
                        phoneNumber = uiState.phoneNumber,
                        onPhoneNumberChange = viewModel::updatePhoneNumber,
                        error = uiState.phoneError,
                        enabled = !uiState.isLoading
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Login Type Toggle
                    LoginTypeToggle(
                        selectedType = uiState.loginType,
                        onTypeSelected = viewModel::switchLoginType,
                        enabled = !uiState.isLoading
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Password or Verification Code Input
                    when (uiState.loginType) {
                        LoginType.PASSWORD -> {
                            PasswordInput(
                                password = uiState.password,
                                onPasswordChange = viewModel::updatePassword,
                                error = uiState.passwordError,
                                enabled = !uiState.isLoading
                            )
                        }
                        LoginType.VERIFICATION_CODE -> {
                            VerificationCodeInput(
                                code = uiState.verificationCode,
                                onCodeChange = viewModel::updateVerificationCode,
                                error = uiState.verificationError,
                                enabled = !uiState.isLoading,
                                isCodeSent = uiState.isCodeSent,
                                countdown = uiState.countdown,
                                onSendCode = viewModel::sendVerificationCode
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    // Login Button
                    Button(
                        onClick = viewModel::login,
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
                                text = "登录",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Register Links
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextButton(
                    onClick = onNavigateToRegistration
                ) {
                    Text(
                        text = "家庭注册（推荐）",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
                
                TextButton(
                    onClick = onNavigateToSignup
                ) {
                    Text(
                        text = "个人注册",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                TextButton(
                    onClick = onNavigateToTeacherRegistration
                ) {
                    Text(
                        text = "教师注册",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
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
            label = { Text("用户名") },
            placeholder = { Text("请输入用户名") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoginTypeToggle(
    selectedType: LoginType,
    onTypeSelected: (LoginType) -> Unit,
    enabled: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        LoginType.values().forEach { type ->
            FilterChip(
                onClick = { onTypeSelected(type) },
                label = {
                    Text(
                        text = when (type) {
                            LoginType.PASSWORD -> "密码登录"
                            LoginType.VERIFICATION_CODE -> "验证码登录"
                        }
                    )
                },
                selected = selectedType == type,
                enabled = enabled,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun PasswordInput(
    password: String,
    onPasswordChange: (String) -> Unit,
    error: String?,
    enabled: Boolean
) {
    var passwordVisible by remember { mutableStateOf(false) }
    
    Column {
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text("密码") },
            placeholder = { Text("请输入密码") },
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
private fun LoginScreenPreview() {
    IStudyTheme {
        LoginScreen(
            onNavigateToStudentHome = {},
            onNavigateToParentHome = {},
            onNavigateToSignup = {},
            onNavigateToRegistration = {},
            onNavigateToTeacherRegistration = {}
        )
    }
}