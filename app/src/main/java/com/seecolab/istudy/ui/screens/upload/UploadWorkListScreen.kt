package com.seecolab.istudy.ui.screens.upload

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.seecolab.istudy.data.model.StudentWorkListItem
import com.seecolab.istudy.ui.screens.ParentWorkListItem
import com.seecolab.istudy.ui.viewmodel.UploadWorkListViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadWorkListScreen(
    studentId: String,
    onWorkItemClick: (StudentWorkListItem) -> Unit = {},
    onNavigateBack: () -> Unit = {},
    viewModel: UploadWorkListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // 初次进入加载
    LaunchedEffect(studentId) {
        viewModel.initStudent(studentId)
        viewModel.search() // 默认加载一次
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        // 顶部栏
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "返回")
            }
            Text(text = "作业列表", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.width(48.dp))
        }

        Spacer(Modifier.height(12.dp))

        // 筛选区
        Card {
            Column(Modifier.fillMaxWidth().padding(16.dp)) {
                Text("筛选", style = MaterialTheme.typography.titleMedium)

                Spacer(Modifier.height(12.dp))

                SubjectDropdown(
                    selected = uiState.subject,
                    onSelected = { viewModel.updateSubject(it) }
                )

                Spacer(Modifier.height(12.dp))

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = uiState.startDate,
                        onValueChange = { viewModel.updateStartDate(it) },
                        label = { Text("开始日期") },
                        placeholder = { Text("YYYY-MM-DD") },
                        trailingIcon = {
                            IconButton(onClick = { viewModel.showDatePicker(isStart = true) }) {
                                Icon(Icons.Default.DateRange, contentDescription = null)
                            }
                        },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = uiState.endDate,
                        onValueChange = { viewModel.updateEndDate(it) },
                        label = { Text("结束日期") },
                        placeholder = { Text("YYYY-MM-DD") },
                        trailingIcon = {
                            IconButton(onClick = { viewModel.showDatePicker(isStart = false) }) {
                                Icon(Icons.Default.DateRange, contentDescription = null)
                            }
                        },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = uiState.maxScore,
                    onValueChange = { viewModel.updateMaxScore(it.filter { ch -> ch.isDigit() }) },
                    label = { Text("成绩低于（分）") },
                    placeholder = { Text("如 80") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(12.dp))

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Button(
                        onClick = { viewModel.search() },
                        modifier = Modifier.weight(1f),
                        enabled = !uiState.isLoading
                    ) {
                        Icon(Icons.Default.Search, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("搜索")
                    }
                    OutlinedButton(
                        onClick = { viewModel.resetAndSearch() },
                        modifier = Modifier.weight(1f),
                        enabled = !uiState.isLoading
                    ) {
                        Text("清除")
                    }
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        // 错误信息
        uiState.errorMessage?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.height(8.dp))
        }

        // 列表或加载状态
        if (uiState.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (uiState.list.isEmpty()) {
            Card(Modifier.fillMaxWidth()) {
                Column(
                    Modifier.fillMaxWidth().padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("暂无作业记录", style = MaterialTheme.typography.titleMedium)
                }
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxSize()) {
                items(uiState.list) { workItem ->
                    ParentWorkListItem(
                        workItem = workItem,
                        isDeleting = false,
                        onClick = { onWorkItemClick(workItem) },
                        onDeleteClick = { /* 家长列表页面通常不在此删除，如需支持可接入 VM */ }
                    )
                }
            }
        }
    }

    // 日期选择器
    if (uiState.isDatePickerVisible) {
        ParentDatePicker(
            isStartDate = uiState.isStartPicker,
            onDateSelected = { date ->
                if (uiState.isStartPicker) viewModel.updateStartDate(date) else viewModel.updateEndDate(date)
                viewModel.hideDatePicker()
            },
            onDismiss = { viewModel.hideDatePicker() }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SubjectDropdown(
    selected: String?,
    onSelected: (String?) -> Unit
) {
    val subjects = listOf("全部","语文","数学","英语","物理","化学","生物","政治","地理","科学","综合")
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
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
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            subjects.forEach { s ->
                DropdownMenuItem(
                    text = { Text(s) },
                    onClick = {
                        onSelected(if (s == "全部") null else s)
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ParentDatePicker(
    isStartDate: Boolean,
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val state = rememberDatePickerState(initialSelectedDateMillis = System.currentTimeMillis())
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                state.selectedDateMillis?.let { millis ->
                    val fmt = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    onDateSelected(fmt.format(Date(millis)))
                }
            }) { Text("确定") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("取消") }
        }
    ) {
        DatePicker(
            state = state,
            title = { Text(if (isStartDate) "选择开始日期" else "选择结束日期", modifier = Modifier.padding(16.dp)) }
        )
    }
}