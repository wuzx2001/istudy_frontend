package com.seecolab.istudy.ui.screens.upload

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.seecolab.istudy.data.model.StudentWorkResponse
import com.seecolab.istudy.data.model.QuestionAnswer
import com.seecolab.istudy.ui.components.KaTeXMathView
import com.seecolab.istudy.ui.viewmodel.UploadResultViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadResultScreen(
    uploadResult: StudentWorkResponse,
    imageUris: List<Uri>,
    onNavigateBack: () -> Unit,
    viewModel: UploadResultViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    
    // Initialize the view model with the upload result
    LaunchedEffect(uploadResult) {
        viewModel.initializeData(uploadResult)
    }
    
    // Show success message
    if (uiState.saveSuccess) {
        LaunchedEffect(uiState.saveSuccess) {
            // Auto-dismiss success state after 2 seconds
            kotlinx.coroutines.delay(2000)
            viewModel.clearSaveSuccess()
        }
    }
    
    // Show error message
    uiState.errorMessage?.let { error ->
        LaunchedEffect(error) {
            // Auto-dismiss error after 3 seconds
            kotlinx.coroutines.delay(3000)
            viewModel.clearError()
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("上传结果") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "返回")
                    }
                },
                actions = {
                    // Save button
                    IconButton(
                        onClick = { viewModel.saveChanges() },
                        enabled = uiState.hasChanges && !uiState.isSaving
                    ) {
                        if (uiState.isSaving) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Icon(
                                Icons.Default.Save, 
                                contentDescription = "保存",
                                tint = if (uiState.hasChanges) MaterialTheme.colorScheme.primary 
                                      else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Status messages
            if (uiState.saveSuccess) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Text(
                        text = "保存成功！",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
            
            uiState.errorMessage?.let { error ->
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Text(
                        text = error,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
            // Upload Information Section
            UploadDetailCard(
                title = "上传信息",
                icon = Icons.Default.Person
            ) {
                UploadDetailItem("学生姓名", uiState.result?.username ?: uiState.result?.student_name ?: "未知")
                // Editable subject field
                EditableDetailItem(
                    label = "学科",
                    value = uiState.editableSubject,
                    onValueChange = viewModel::updateSubject,
                    placeholder = "请输入学科"
                )
                // Editable paper name field
                EditableDetailItem(
                    label = "考试名称",
                    value = uiState.editablePaperName,
                    onValueChange = viewModel::updatePaperName,
                    placeholder = "请输入考试名称"
                )
                UploadDetailItem("上传时间", formatDate(uiState.result?.action_date ?: ""))
                uiState.result?.score?.let { score ->
                    UploadDetailItem("得分", "$score 分")
                }
            }

            // Uploaded Images Section
            if (imageUris.isNotEmpty()) {
                UploadDetailCard(
                    title = "上传的图片",
                    icon = Icons.Default.Image
                ) {
                    Text(
                        text = "共上传 ${imageUris.size} 张图片",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(8.dp)
                    )
                    Button(
                        onClick = {
                            // Here you could implement image viewing functionality
                            // For now, we'll just show a simple message
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Image, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("查看上传的图片")
                    }
                }
            }

            // Right Answer Section - Always show block
            UploadDetailCard(
                title = "正确答案",
                icon = Icons.Default.Star
            ) {
                val result = uiState.result
                if (!result?.right_answer.isNullOrEmpty()) {
                    result?.right_answer?.forEachIndexed { index, qa ->
                        UploadQuestionAnswerItem(
                            questionAnswer = qa,
                            index = index + 1,
                            isCorrect = true
                        )
                        if (index < result.right_answer!!.size - 1) {
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
            UploadDetailCard(
                title = "错误分析",
                icon = Icons.Default.Star
            ) {
                val result = uiState.result
                if (!result?.wrong_answer.isNullOrEmpty()) {
                    result?.wrong_answer?.forEachIndexed { index, qa ->
                        UploadQuestionAnswerItem(
                            questionAnswer = qa,
                            index = index + 1,
                            isCorrect = false
                        )
                        if (index < result.wrong_answer!!.size - 1) {
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
            UploadDetailCard(
                title = "学习建议",
                icon = Icons.Default.Star
            ) {
                val result = uiState.result
                if (!result?.suggestion.isNullOrEmpty()) {
                    Text(
                        text = result?.suggestion ?: "",
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
}

@Composable
fun UploadDetailCard(
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
fun UploadDetailItem(
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditableDetailItem(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun UploadQuestionAnswerItem(
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