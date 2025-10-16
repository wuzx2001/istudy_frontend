package com.seecolab.istudy.ui.screens.workdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.seecolab.istudy.data.model.StudentWorkResponse
import com.seecolab.istudy.data.model.QuestionAnswer
import com.seecolab.istudy.ui.components.KaTeXMathView
import com.seecolab.istudy.ui.viewmodel.StudentWorkDetailViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentWorkDetailScreen(
    workId: String,
    onNavigateBack: () -> Unit,
    viewModel: StudentWorkDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    LaunchedEffect(workId) {
        viewModel.loadWorkDetail(workId)
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("作业详情") },
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
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                uiState.errorMessage != null -> {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        )
                    ) {
                        Text(
                            text = uiState.errorMessage!!,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
                uiState.workDetail != null -> {
                    WorkDetailContent(
                        workDetail = uiState.workDetail!!,
                        onShowImage = { imageUrl -> viewModel.showImageDialog(imageUrl) },
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
        
        // Image Dialog
        if (uiState.showImageDialog && !uiState.imageUrl.isNullOrEmpty()) {
            Dialog(
                onDismissRequest = viewModel::hideImageDialog,
                properties = DialogProperties(
                    usePlatformDefaultWidth = false
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                        .clickable { viewModel.hideImageDialog() }
                ) {
                    AsyncImage(
                        model = uiState.imageUrl,
                        contentDescription = "试卷图片",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentScale = ContentScale.Fit
                    )
                    
                    IconButton(
                        onClick = viewModel::hideImageDialog,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(16.dp)
                            .background(
                                Color.Black.copy(alpha = 0.5f),
                                CircleShape
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "关闭",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun WorkDetailContent(
    workDetail: StudentWorkResponse,
    onShowImage: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Work Details Section
        WorkDetailCard(
            title = "考试信息",
            icon = Icons.Default.Person
        ) {
            WorkDetailItem("学生姓名", workDetail.username ?: workDetail.student_name ?: "未知")
            WorkDetailItem("学科", workDetail.subject ?: "未知")
            WorkDetailItem("考试名称", workDetail.paper_name ?: "作业 #${workDetail.id.take(8)}...")
            WorkDetailItem("考试时间", formatDate(workDetail.action_date))
            if (workDetail.score != null) {
                WorkDetailItem("得分", "${workDetail.score} 分")
            }
        }

        // Paper Image Section
        if (workDetail.paper.isNotEmpty()) {
            WorkDetailCard(
                title = "试卷图片",
                icon = Icons.Default.Image
            ) {
                Button(
                    onClick = {
                        onShowImage(workDetail.paper)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Image, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("查看试卷图片")
                }
                if (workDetail.oss_id != null) {
                    Text(
                        text = "文件名: ${workDetail.oss_id}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }

        // Right Answer Section - Always show block
        WorkDetailCard(
            title = "正确答案",
            icon = Icons.Default.Star
        ) {
            if (!workDetail.right_answer.isNullOrEmpty()) {
                workDetail.right_answer.forEachIndexed { index, qa ->
                    QuestionAnswerItem(
                        questionAnswer = qa,
                        index = index + 1,
                        isCorrect = true
                    )
                    if (index < workDetail.right_answer.size - 1) {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            } else {
                Text(
                    text = "暂无正确答案内容",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        // Wrong Answer Section - Always show block
        WorkDetailCard(
            title = "错误分析",
            icon = Icons.Default.Star
        ) {
            if (!workDetail.wrong_answer.isNullOrEmpty()) {
                workDetail.wrong_answer.forEachIndexed { index, qa ->
                    QuestionAnswerItem(
                        questionAnswer = qa,
                        index = index + 1,
                        isCorrect = false
                    )
                    if (index < workDetail.wrong_answer.size - 1) {
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

        // Suggestions Section - Always show block
        WorkDetailCard(
            title = "学习建议",
            icon = Icons.Default.Star
        ) {
            if (!workDetail.suggestion.isNullOrEmpty()) {
                Text(
                    text = workDetail.suggestion,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            } else {
                Text(
                    text = "暂无学习建议内容",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun WorkDetailCard(
    title: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )
            }
            
            content()
        }
    }
}

@Composable
fun WorkDetailItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun QuestionAnswerItem(
    questionAnswer: QuestionAnswer,
    index: Int,
    isCorrect: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        // Question with index
        Text(
            text = "题目 $index",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Medium,
            color = if (isCorrect) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        // Question content with KaTeX rendering
        KaTeXMathView(
            mathText = questionAnswer.question,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        
        // Student answer and correct answer
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "学生答案",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = questionAnswer.student_answer,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
            }
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "正确答案",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = questionAnswer.correct_answer,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        
        // Description with KaTeX rendering
        if (questionAnswer.description.isNotEmpty()) {
            Text(
                text = "解析",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            KaTeXMathView(
                mathText = questionAnswer.description,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

private fun formatDate(dateString: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy年MM月dd日 HH:mm", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        outputFormat.format(date ?: Date())
    } catch (e: Exception) {
        dateString
    }
}