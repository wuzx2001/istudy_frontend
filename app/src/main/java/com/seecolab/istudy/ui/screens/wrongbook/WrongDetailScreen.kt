package com.seecolab.istudy.ui.screens.wrongbook

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.seecolab.istudy.data.model.QuestionAnswer
import com.seecolab.istudy.ui.components.KaTeXMathView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WrongDetailScreen(
    workId: String,
    onNavigateBack: () -> Unit,
    viewModel: WrongDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(workId) {
        viewModel.loadWrongDetail(workId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("错题详情") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "返回")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                uiState.errorMessage != null -> {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
                    ) {
                        Text(
                            text = uiState.errorMessage!!,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
                uiState.detail != null -> {
                    WrongDetailContent(
                        detail = uiState.detail!!,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                else -> {
                    Text(
                        text = "暂无数据",
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@Composable
private fun WrongDetailContent(
    detail: com.seecolab.istudy.data.model.WrongDetailResponse,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 基本信息
        DetailCard(title = "考试信息", icon = Icons.Default.Person) {
            DetailItem("学科", detail.subject ?: "未知")
            DetailItem("考试名称", detail.paper_name ?: "作业 #${detail.work_id.take(8)}")
        }

        // 错题列表
        DetailCard(title = "错误分析", icon = Icons.Default.Star) {
            if (detail.wrong_answer.isNotEmpty()) {
                detail.wrong_answer.forEachIndexed { index, qa ->
                    WrongQAItem(qa, index + 1)
                    if (index < detail.wrong_answer.size - 1) {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            } else {
                Text(
                    text = "暂无错误分析内容",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Composable
private fun DetailCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 12.dp)) {
                Icon(imageVector = icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Medium)
            }
            content()
        }
    }
}

@Composable
private fun DetailItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(text = value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun WrongQAItem(qa: QuestionAnswer, index: Int) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "题目 $index",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        KaTeXMathView(
            mathText = qa.question,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "学生答案", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(text = qa.student_answer, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "正确答案", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(text = qa.correct_answer, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.primary)
            }
        }
        if (qa.description.isNotEmpty()) {
            Text(text = "解析", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 4.dp))
            KaTeXMathView(mathText = qa.description, modifier = Modifier.fillMaxWidth())
        }
    }
}