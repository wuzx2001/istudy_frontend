package com.seecolab.istudy.ui.screens.teacher

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeacherDetailScreen(
    userId: String,
    onNavigateBack: () -> Unit,
    viewModel: TeacherDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(userId) {
        viewModel.load(userId)
    }
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("教师详情") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "返回")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)) {
            when {
                uiState.isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                uiState.errorMessage != null -> {
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)) {
                        Text(text = "加载失败：${uiState.errorMessage}", color = MaterialTheme.colorScheme.error)
                        Button(onClick = { viewModel.load(userId) }, modifier = Modifier.padding(top = 12.dp)) {
                            Text("重试")
                        }
                    }
                }
                else -> {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = "姓名：${uiState.name}", style = MaterialTheme.typography.titleMedium)
                        Text(text = "性别：${uiState.sex}", style = MaterialTheme.typography.bodyMedium)
                        Text(text = "科目：${uiState.subjects.joinToString("、")}", style = MaterialTheme.typography.bodyMedium)
                        if (uiState.grades.isNotEmpty()) {
                            Text(text = "年级：${uiState.grades.joinToString("、")}", style = MaterialTheme.typography.bodyMedium)
                        }
                        if (uiState.address.isNotEmpty()) {
                            Text(text = "地区：${uiState.address}", style = MaterialTheme.typography.bodyMedium)
                        }
                        if (uiState.telephone.isNotEmpty()) {
                            Text(text = "电话：${uiState.telephone}", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}