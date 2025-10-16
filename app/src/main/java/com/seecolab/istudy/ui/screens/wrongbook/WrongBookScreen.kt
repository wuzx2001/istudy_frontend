package com.seecolab.istudy.ui.screens.wrongbook

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.seecolab.istudy.data.model.StudentWorkListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WrongBookScreen(
    initialStudentId: String? = null,
    viewModel: WrongBookViewModel = hiltViewModel(),
    onWorkItemClick: (StudentWorkListItem) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(initialStudentId) {
        // 若从家长入口带入 studentId，则设入 ViewModel
        if (!initialStudentId.isNullOrBlank()) {
            viewModel.setStudentId(initialStudentId)
        }
        // 默认不自动加载，等待筛选提交
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("错题本筛选", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(12.dp))

        // Filters
        // 学科下拉选择
        var subjectMenuExpanded by remember { mutableStateOf(false) }
        val subjectOptions = listOf("语文","数学","英语","物理","化学","生物","政治","地理","科学","综合")
        ExposedDropdownMenuBox(
            expanded = subjectMenuExpanded,
            onExpandedChange = { subjectMenuExpanded = !subjectMenuExpanded }
        ) {
            TextField(
                value = uiState.subject,
                onValueChange = {},
                readOnly = true,
                label = { Text("学科（必选）") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = subjectMenuExpanded)
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = subjectMenuExpanded,
                onDismissRequest = { subjectMenuExpanded = false }
            ) {
                subjectOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            viewModel.updateSubject(option)
                            subjectMenuExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = uiState.startDate ?: "",
            onValueChange = { viewModel.updateStartDate(if (it.isBlank()) null else it) },
            label = { Text("开始日期（可选，YYYY-MM-DD）") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = uiState.endDate ?: "",
            onValueChange = { viewModel.updateEndDate(if (it.isBlank()) null else it) },
            label = { Text("结束日期（可选，YYYY-MM-DD）") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = { viewModel.searchWrongQuestions() },
                enabled = uiState.subject.isNotBlank() && !uiState.isLoading
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("查询")
                }
            }
            OutlinedButton(onClick = { viewModel.clearFilters() }, enabled = !uiState.isLoading) {
                Text("清空")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        uiState.errorMessage?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Results
        if (uiState.results.isNotEmpty()) {
            Text(
                text = "共 ${uiState.results.size} 条错题",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(uiState.results) { item ->
                    ResultItemCard(item = item, onClick = { onWorkItemClick(item) })
                }
            }
        } else if (!uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("暂无数据，请先设置筛选条件进行查询。")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ResultItemCard(
    item: StudentWorkListItem,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            // 标题
            Text(
                text = item.paper_name?.takeIf { it.isNotEmpty() } ?: "作业 #${item.work_id.take(8)}",
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.height(6.dp))
            // 次要信息行：科目与错题数
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                item.subject?.let {
                    Text("科目：$it", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                val wc = item.wrong_count ?: 0
                Text("错题数：$wc", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
            }
            Spacer(modifier = Modifier.height(6.dp))
            // 底部信息：时间
            Text(
                "上传时间：${item.create_time}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}