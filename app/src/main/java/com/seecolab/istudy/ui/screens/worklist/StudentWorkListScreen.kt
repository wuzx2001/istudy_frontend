package com.seecolab.istudy.ui.screens.worklist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.seecolab.istudy.data.model.StudentWorkListItem
import com.seecolab.istudy.data.model.UserRole
import com.seecolab.istudy.ui.viewmodel.StudentWorkListViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentWorkListScreen(
    onWorkItemClick: (StudentWorkListItem) -> Unit = {},
    onUploadClick: () -> Unit = {},
    viewModel: StudentWorkListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    var isStartDatePicker by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Text(
            text = "作业列表",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Search Section
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "筛选",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                // 学科下拉 + 日期段
                SubjectDropdown(
                    selected = uiState.selectedSubject,
                    onSelected = { viewModel.setSubject(it) }
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Start Date
                    OutlinedTextField(
                        value = startDate,
                        onValueChange = { startDate = it },
                        label = { Text("开始日期") },
                        placeholder = { Text("YYYY-MM-DD") },
                        trailingIcon = {
                            IconButton(onClick = {
                                isStartDatePicker = true
                                showDatePicker = true
                            }) {
                                Icon(Icons.Default.DateRange, contentDescription = "Select start date")
                            }
                        },
                        modifier = Modifier.weight(1f)
                    )

                    // End Date
                    OutlinedTextField(
                        value = endDate,
                        onValueChange = { endDate = it },
                        label = { Text("结束日期") },
                        placeholder = { Text("YYYY-MM-DD") },
                        trailingIcon = {
                            IconButton(onClick = {
                                isStartDatePicker = false
                                showDatePicker = true
                            }) {
                                Icon(Icons.Default.DateRange, contentDescription = "Select end date")
                            }
                        },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Search Button
                    Button(
                        onClick = {
                            viewModel.searchByDateRange(startDate, endDate)
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.Search, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("搜索")
                    }

                    // Clear Button
                    OutlinedButton(
                        onClick = {
                            startDate = ""
                            endDate = ""
                            viewModel.refreshWorkList()
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("清除")
                    }
                }
            }
        }

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

        // Work List
        Box(modifier = Modifier.fillMaxSize()) {
            if (uiState.isLoading && uiState.workList.isEmpty()) {
                // Initial loading
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (uiState.workList.isEmpty()) {
                // Empty state
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "暂无作业记录",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "您还没有上传过作业，点击学习建议页面上传作业",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                LazyColumn(
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(uiState.workList) { workItem ->
                        WorkListItem(
                            workItem = workItem,
                            currentUser = uiState.currentUser,
                            isDeleting = uiState.isDeleting,
                            onClick = { onWorkItemClick(workItem) },
                            onDeleteClick = { viewModel.deleteStudentWork(workItem) }
                        )
                    }

                    // Load more indicator
                    if (uiState.hasMorePages) {
                        item {
                            LaunchedEffect(Unit) {
                                viewModel.loadMoreWorks()
                            }
                            
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(modifier = Modifier.size(24.dp))
                            }
                        }
                    }
                }
            }
        }
    }

    // Date Picker with proper calendar functionality
    if (showDatePicker) {
        DatePickerDialog(
            isStartDate = isStartDatePicker,
            onDateSelected = { dateString ->
                if (isStartDatePicker) {
                    startDate = dateString
                } else {
                    endDate = dateString
                }
                showDatePicker = false
            },
            onDismiss = { showDatePicker = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkListItem(
    workItem: StudentWorkListItem,
    currentUser: com.seecolab.istudy.data.model.User?,
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
    val isParentUser = currentUser?.role == UserRole.PARENT

    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
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
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "科目: ${workItem.subject ?: "未知"}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = workItem.paper_name?.takeIf { it.isNotEmpty() }
                            ?: "作业 #${workItem.work_id.take(8.coerceAtMost(workItem.work_id.length))}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    StatusChip(status = workItem.status ?: "completed")
                    
                    // Delete button - only visible to parent users
                    if (isParentUser) {
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
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

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

@Composable
fun StatusChip(status: String) {
    val (backgroundColor, textColor, text) = when (status.lowercase()) {
        "completed" -> Triple(
            MaterialTheme.colorScheme.primaryContainer,
            MaterialTheme.colorScheme.onPrimaryContainer,
            "已完成"
        )
        "pending" -> Triple(
            MaterialTheme.colorScheme.secondaryContainer,
            MaterialTheme.colorScheme.onSecondaryContainer,
            "处理中"
        )
        "processing" -> Triple(
            MaterialTheme.colorScheme.tertiaryContainer,
            MaterialTheme.colorScheme.onTertiaryContainer,
            "批改中"
        )
        else -> Triple(
            MaterialTheme.colorScheme.surfaceVariant,
            MaterialTheme.colorScheme.onSurfaceVariant,
            status
        )
    }

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = backgroundColor
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = textColor,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SubjectDropdown(
    selected: String?,
    onSelected: (String?) -> Unit
) {
    val subjects = listOf("语文","数学","英语","物理","化学","生物","政治","地理","科学","综合")
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            value = selected ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text("学科") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("全部") },
                onClick = {
                    onSelected(null)
                    expanded = false
                }
            )
            subjects.forEach { s ->
                DropdownMenuItem(
                    text = { Text(s) },
                    onClick = {
                        onSelected(s)
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    isStartDate: Boolean,
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis()
    )
    
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        val dateString = formatter.format(Date(millis))
                        onDateSelected(dateString)
                    }
                }
            ) {
                Text("确定")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("取消")
            }
        }
    ) {
        DatePicker(
            state = datePickerState,
            title = {
                Text(
                    text = if (isStartDate) "选择开始日期" else "选择结束日期",
                    modifier = Modifier.padding(16.dp)
                )
            }
        )
    }
}