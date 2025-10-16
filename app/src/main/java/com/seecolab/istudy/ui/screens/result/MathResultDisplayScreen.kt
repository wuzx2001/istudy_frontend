package com.seecolab.istudy.ui.screens.result

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.seecolab.istudy.data.model.QuestionAnswer
import com.seecolab.istudy.data.model.StudentWorkResponse
import com.seecolab.istudy.ui.components.EnhancedKaTeXDisplay
import com.seecolab.istudy.ui.viewmodel.CorrectionResultUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MathResultDisplayScreen(
    uiState: CorrectionResultUiState,
    onNavigateBack: () -> Unit,
    onShowImages: () -> Unit
) {
    // Debug logging
    android.util.Log.d("MathResultDisplayScreen", "Result: ${uiState.result != null}, Images: ${uiState.imageUris.size}")
    
    val scrollState = rememberScrollState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.05f),
                        MaterialTheme.colorScheme.background
                    )
                )
            )
    ) {
        // Modern Top Bar
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 8.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onNavigateBack,
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            MaterialTheme.colorScheme.primaryContainer,
                            RoundedCornerShape(20.dp)
                        )
                ) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "返回",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "数学分析结果",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "AI 智能批改",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                
                // Images button or refresh button
                if (uiState.imageUris.isNotEmpty()) {
                    FloatingActionButton(
                        onClick = onShowImages,
                        modifier = Modifier.size(40.dp),
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer
                    ) {
                        Icon(
                            Icons.Default.PhotoLibrary,
                            contentDescription = "查看图片",
                            modifier = Modifier.size(20.dp),
                            tint = MaterialTheme.colorScheme.onTertiaryContainer
                        )
                    }
                } else {
                    FloatingActionButton(
                        onClick = {
                            android.util.Log.d("MathResultDisplayScreen", "Manual refresh clicked")
                            android.util.Log.d("MathResultDisplayScreen", "Current state: result=${uiState.result != null}")
                        },
                        modifier = Modifier.size(40.dp),
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    ) {
                        Icon(
                            Icons.Default.Refresh,
                            contentDescription = "刷新",
                            modifier = Modifier.size(20.dp),
                            tint = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }
        }
        
        // Main Content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(vertical = 24.dp)
        ) {
            // Score Card (if available)
            uiState.result?.score?.let { score ->
                item {
                    ScoreCard(score = score)
                }
            }
            
            // AI Analysis Section - Show structured result data
            item {
                if (uiState.result != null) {
                    // Show structured analysis based on result data
                    ResultAnalysisCard(result = uiState.result)
                } else {
                    // Fallback when no result
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(bottom = 16.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.SmartToy,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = "AI 详细分析",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            
                            Text(
                                text = "暂无分析结果",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            
                            // Debug info
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "Debug: Result = ${uiState.result != null}, Images = ${uiState.imageUris.size}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    }
                }
            }
            
            // Results Summary
            uiState.result?.let { result ->
                // Correct Answers
                result.right_answer?.takeIf { it.isNotEmpty() }?.let { rightAnswers ->
                    item {
                        QuestionAnswerCard(
                            title = "正确答案",
                            questions = rightAnswers,
                            icon = Icons.Default.CheckCircle,
                            cardColor = Color(0xFF4CAF50).copy(alpha = 0.1f),
                            iconColor = Color(0xFF4CAF50),
                            isCorrect = true
                        )
                    }
                }
                
                // Wrong Answers
                result.wrong_answer?.takeIf { it.isNotEmpty() }?.let { wrongAnswers ->
                    item {
                        QuestionAnswerCard(
                            title = "错误分析",
                            questions = wrongAnswers,
                            icon = Icons.Default.Cancel,
                            cardColor = Color(0xFFF44336).copy(alpha = 0.1f),
                            iconColor = Color(0xFFF44336),
                            isCorrect = false
                        )
                    }
                }
                
                // Suggestions
                result.suggestion?.takeIf { it.isNotEmpty() }?.let { suggestion ->
                    item {
                        SuggestionCard(suggestion = suggestion)
                    }
                }
            }
            
            // Bottom spacing
            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
private fun ScoreCard(score: Float) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = when {
                score >= 90 -> Color(0xFF4CAF50).copy(alpha = 0.15f)
                score >= 80 -> Color(0xFF2196F3).copy(alpha = 0.15f)
                score >= 60 -> Color(0xFFFF9800).copy(alpha = 0.15f)
                else -> Color(0xFFF44336).copy(alpha = 0.15f)
            }
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "总分",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                
                Text(
                    text = "$score",
                    style = MaterialTheme.typography.displayLarge,
                    fontWeight = FontWeight.Bold,
                    color = when {
                        score >= 90 -> Color(0xFF4CAF50)
                        score >= 80 -> Color(0xFF2196F3)
                        score >= 60 -> Color(0xFFFF9800)
                        else -> Color(0xFFF44336)
                    }
                )
                
                Text(
                    text = "/100",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
                
                // Performance indicator
                Text(
                    text = when {
                        score >= 90 -> "优秀 🎉"
                        score >= 80 -> "良好 👍"
                        score >= 60 -> "及格 📝"
                        else -> "需要努力 💪"
                    },
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
private fun AnalysisCard(
    title: String,
    icon: ImageVector,
    content: String,
    cardColor: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            
            // Use KaTeX to render LaTeX expressions properly
            EnhancedKaTeXDisplay(
                text = content,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


@Composable
private fun QuestionAnswerCard(
    title: String,
    questions: List<QuestionAnswer>,
    icon: ImageVector,
    cardColor: Color,
    iconColor: Color,
    isCorrect: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = iconColor
                )
            }
            
            // Display each question-answer pair
            questions.forEachIndexed { index, qa ->
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Question number
                    Text(
                        text = "题目 ${index + 1}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium,
                        color = iconColor,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    
                    // Question text with KaTeX rendering
                    EnhancedKaTeXDisplay(
                        text = qa.question,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                    
                    // Answer information
                    if (isCorrect) {
                        Text(
                            text = "正确答案: ${qa.correct_answer}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            color = iconColor,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    } else {
                        Text(
                            text = "学生答案: ${qa.student_answer} | 正确答案: ${qa.correct_answer}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            color = iconColor,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                    
                    // Description with KaTeX rendering
                    if (qa.description.isNotEmpty()) {
                        Text(
                            text = "解析:",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        EnhancedKaTeXDisplay(
                            text = qa.description,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    
                    // Add spacing between questions
                    if (index < questions.size - 1) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Divider(
                            color = iconColor.copy(alpha = 0.3f),
                            thickness = 1.dp,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SuggestionCard(suggestion: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Lightbulb,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onTertiaryContainer,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "改进建议",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
            }
            
            Text(
                text = suggestion,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                lineHeight = MaterialTheme.typography.bodyLarge.lineHeight
            )
        }
    }
}

/**
 * Component to display structured result data instead of streaming content
 */
@Composable
private fun ResultAnalysisCard(result: StudentWorkResponse) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Right answers section
        if (!result.right_answer.isNullOrEmpty()) {
            QuestionAnswerCard(
                title = "正确答案 (${result.right_answer.size} 题)",
                questions = result.right_answer,
                icon = Icons.Default.CheckCircle,
                cardColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                iconColor = Color(0xFF4CAF50),
                isCorrect = true
            )
        }
        
        // Wrong answers section
        if (!result.wrong_answer.isNullOrEmpty()) {
            QuestionAnswerCard(
                title = "错误分析 (${result.wrong_answer.size} 题)",
                questions = result.wrong_answer,
                icon = Icons.Default.Cancel,
                cardColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f),
                iconColor = Color(0xFFF44336),
                isCorrect = false
            )
        }
        
        // Suggestions section
        if (!result.suggestion.isNullOrEmpty()) {
            SuggestionCard(suggestion = result.suggestion)
        }
    }
}