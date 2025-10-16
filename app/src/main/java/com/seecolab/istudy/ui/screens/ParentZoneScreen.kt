package com.seecolab.istudy.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.seecolab.istudy.R
import com.seecolab.istudy.data.model.StudentByRoleData
import com.seecolab.istudy.data.model.StudentWorkListItem
import com.seecolab.istudy.ui.viewmodel.ParentZoneViewModel
import java.text.SimpleDateFormat
import java.util.*

data class ReportSection(
    val title: String,
    val icon: ImageVector,
    val content: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParentZoneScreen(
    onNavigateToUserInfo: () -> Unit = {},
    onWorkItemClick: (StudentWorkListItem) -> Unit = {},
    onNavigateToParentWorkList: (StudentByRoleData) -> Unit = {},
    onNavigateToWrongBook: (StudentByRoleData) -> Unit = {},
    onNavigateToStudyTrend: () -> Unit = {},
    onNavigateToAnalysis: () -> Unit = {},
    viewModel: ParentZoneViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadLearningReport()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.nav_parent_zone),
                style = MaterialTheme.typography.headlineMedium
            )
            
            IconButton(
                onClick = onNavigateToUserInfo
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "用户管理",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Error Message
        uiState.errorMessage?.let { error ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Text(
                    text = error,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
        
        // Success Message for Delete
        uiState.deleteMessage?.let { message ->
            LaunchedEffect(message) {
                kotlinx.coroutines.delay(2000)
                viewModel.clearDeleteMessage()
            }
            
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
        
        // Student Selection Section
        if (uiState.students.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "学生列表",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    
                    uiState.students.forEach { student ->
                        StudentSelectionCard(
                            student = student,
                            isSelected = uiState.selectedStudent?.user_id == student.user_id,
                            onClick = { viewModel.selectStudent(student) },
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
        }
        
        // Student Quick Actions (replace original inline work list)
        uiState.selectedStudent?.let { selectedStudent ->
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "${selectedStudent.username} 的学习报告",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            // 学习趋势
                            ElevatedCard(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { onNavigateToStudyTrend() }
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.TrendingUp,
                                        contentDescription = null
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text("学习趋势", style = MaterialTheme.typography.bodyMedium)
                                }
                            }
                            // 学生画像
                            ElevatedCard(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { onNavigateToAnalysis() }
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = null
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text("学生画像", style = MaterialTheme.typography.bodyMedium)
                                }
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            // 作业列表
                            ElevatedCard(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { onNavigateToParentWorkList(selectedStudent) }
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.List,
                                        contentDescription = null
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text("作业列表", style = MaterialTheme.typography.bodyMedium)
                                }
                            }
                            // 错题本
                            ElevatedCard(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { onNavigateToWrongBook(selectedStudent) }
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = null
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text("错题本", style = MaterialTheme.typography.bodyMedium)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StudentSelectionCard(
    student: StudentByRoleData,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) 
                MaterialTheme.colorScheme.primaryContainer 
            else 
                MaterialTheme.colorScheme.surfaceVariant
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.School,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = if (isSelected) 
                    MaterialTheme.colorScheme.onPrimaryContainer 
                else 
                    MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = student.username,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                    color = if (isSelected) 
                        MaterialTheme.colorScheme.onPrimaryContainer 
                    else 
                        MaterialTheme.colorScheme.onSurface
                )
                
                Text(
                    text = "${student.grade} • ${student.sex} • ${student.age}岁",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isSelected) 
                        MaterialTheme.colorScheme.onPrimaryContainer 
                    else 
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "已选中",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}

@Composable
fun ParentWorkListItem(
    workItem: StudentWorkListItem,
    isDeleting: Boolean,
    onClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    val formattedDate = try {
        val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).parse(workItem.create_time)
        dateFormat.format(date ?: Date())
    } catch (e: Exception) {
        workItem.create_time
    }
    
    var showDeleteDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = workItem.paper_name?.takeIf { it.isNotEmpty() } ?: "作业 #${workItem.work_id.take(8)}",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Medium
                    )
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    if (workItem.subject != null) {
                        Text(
                            text = "科目: ${workItem.subject}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Status Chip
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = when (workItem.status?.lowercase()) {
                            "completed" -> MaterialTheme.colorScheme.primaryContainer
                            "pending" -> MaterialTheme.colorScheme.secondaryContainer
                            "processing" -> MaterialTheme.colorScheme.tertiaryContainer
                            else -> MaterialTheme.colorScheme.surfaceVariant
                        }
                    ) {
                        Text(
                            text = when (workItem.status?.lowercase()) {
                                "completed" -> "已完成"
                                "pending" -> "处理中"
                                "processing" -> "批改中"
                                else -> workItem.status ?: "已完成"
                            },
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                    
                    // Delete button - always visible to parent users
                    IconButton(
                        onClick = { showDeleteDialog = true },
                        enabled = !isDeleting
                    ) {
                        if (isDeleting) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "删除",
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "上传时间: $formattedDate",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                if (workItem.score != null) {
                    Text(
                        text = "得分: ${workItem.score}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
    
    // Delete confirmation dialog
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("确认删除") },
            text = { 
                Text(
                    "确定要删除这个作业吗？\n\n作业: ${workItem.paper_name?.takeIf { it.isNotEmpty() } ?: "作业 #${workItem.work_id.take(8)}"}"
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeleteClick()
                        showDeleteDialog = false
                    },
                    enabled = !isDeleting
                ) {
                    Text("删除", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDeleteDialog = false }
                ) {
                    Text("取消")
                }
            }
        )
    }
}