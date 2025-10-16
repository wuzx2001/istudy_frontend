package com.seecolab.istudy.ui.screens.result

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.seecolab.istudy.ui.components.EnhancedKaTeXDisplay
import com.seecolab.istudy.ui.viewmodel.CorrectionResultUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CorrectionResultScreen(
    uiState: CorrectionResultUiState,
    onNavigateBack: () -> Unit,
    onShowImageDialog: (Int) -> Unit,
    onHideImageDialog: () -> Unit,
    onSelectImage: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top bar
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "返回")
                }
                Text(
                    text = "AI 分析结果",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                // View images button
                if (uiState.imageUris.isNotEmpty()) {
                    OutlinedButton(
                        onClick = { onShowImageDialog(0) },
                        modifier = Modifier.width(88.dp),
                        contentPadding = PaddingValues(horizontal = 6.dp, vertical = 4.dp)
                    ) {
                        Icon(
                            Icons.Default.Image,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            "查看图片",
                            style = MaterialTheme.typography.labelSmall,
                            maxLines = 1
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.width(88.dp))
                }
            }
        }
        
        // Scrollable content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Show results if available
            uiState.result?.let { response ->
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.SmartToy,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "AI 详细分析",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            Text(
                                text = "分析完成，请查看下面的详细结果。",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            } ?: run {
                // Show fallback when no result
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.SmartToy,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "AI 详细分析",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            Text(
                                text = "暂无分析结果",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
            
            // Final Results Summary
            uiState.result?.let { response ->
                item {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.Assessment,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "成绩总结",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                            
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            // Score
                            response.score?.let { score ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "总分:",
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.Medium,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                    Text(
                                        text = "$score/100",
                                        style = MaterialTheme.typography.headlineSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = if (score >= 80) Color(0xFF4CAF50) 
                                               else if (score >= 60) Color(0xFFFF9800) 
                                               else Color(0xFFF44336)
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                            
                            // Correct Answers
                            response.right_answer?.let { rightAnswers ->
                                if (rightAnswers.isNotEmpty()) {
                                    Text(
                                        text = "正确答案:",
                                        style = MaterialTheme.typography.labelLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF4CAF50)
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    rightAnswers.forEachIndexed { index, qa ->
                                        Text(
                                            text = "题目 ${index + 1}",
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.Medium,
                                            color = Color(0xFF4CAF50),
                                            modifier = Modifier.padding(bottom = 4.dp)
                                        )
                                        EnhancedKaTeXDisplay(
                                            text = qa.question,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                        Text(
                                            text = "答案: ${qa.correct_answer}",
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.Medium,
                                            modifier = Modifier.padding(vertical = 4.dp)
                                        )
                                        if (qa.description.isNotEmpty()) {
                                            EnhancedKaTeXDisplay(
                                                text = qa.description,
                                                modifier = Modifier.fillMaxWidth()
                                            )
                                        }
                                        if (index < rightAnswers.size - 1) {
                                            Spacer(modifier = Modifier.height(8.dp))
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(12.dp))
                                }
                            }
                            
                            // Wrong Answers
                            response.wrong_answer?.let { wrongAnswers ->
                                if (wrongAnswers.isNotEmpty()) {
                                    Text(
                                        text = "错误答案:",
                                        style = MaterialTheme.typography.labelLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFFF44336)
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    wrongAnswers.forEachIndexed { index, qa ->
                                        Text(
                                            text = "题目 ${index + 1}",
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.Medium,
                                            color = Color(0xFFF44336),
                                            modifier = Modifier.padding(bottom = 4.dp)
                                        )
                                        EnhancedKaTeXDisplay(
                                            text = qa.question,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                        Text(
                                            text = "学生答案: ${qa.student_answer} | 正确答案: ${qa.correct_answer}",
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.Medium,
                                            modifier = Modifier.padding(vertical = 4.dp)
                                        )
                                        if (qa.description.isNotEmpty()) {
                                            EnhancedKaTeXDisplay(
                                                text = qa.description,
                                                modifier = Modifier.fillMaxWidth()
                                            )
                                        }
                                        if (index < wrongAnswers.size - 1) {
                                            Spacer(modifier = Modifier.height(8.dp))
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(12.dp))
                                }
                            }
                            
                            // Suggestions
                            response.suggestion?.let { suggestion ->
                                if (suggestion.isNotEmpty()) {
                                    Text(
                                        text = "改进建议:",
                                        style = MaterialTheme.typography.labelLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = suggestion,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                                        lineHeight = MaterialTheme.typography.bodyMedium.lineHeight
                                    )
                                }
                            }
                        }
                    }
                }
            }
            
            // Bottom spacing
            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
    
    // Image dialog
    if (uiState.showImageDialog && uiState.imageUris.isNotEmpty()) {
        com.seecolab.istudy.ui.screens.result.ImageViewerDialog(
            imageUris = uiState.imageUris,
            initialIndex = uiState.selectedImageIndex,
            onDismiss = onHideImageDialog,
            onImageSelected = onSelectImage
        )
    }
}