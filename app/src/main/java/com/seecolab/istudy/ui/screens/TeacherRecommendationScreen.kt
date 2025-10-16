package com.seecolab.istudy.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.seecolab.istudy.R
import com.seecolab.istudy.data.model.Teacher
import com.seecolab.istudy.ui.viewmodel.TeacherRecommendationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeacherRecommendationScreen(
    viewModel: TeacherRecommendationViewModel = hiltViewModel(),
    onNavigateToTeacherDetail: (String) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.nav_teacher_recommendation),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Filter Section
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.filter_teachers),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                // 三个下拉筛选：学科、性别、区域
                val subjects = listOf("语文","数学","英语","物理","化学","生物","政治","地理","科学","综合")
                val genders = listOf("男","女")
                val regions = listOf(
                    "黄浦区","徐汇区","长宁区","静安区","普陀区","虹口区","杨浦区",
                    "闵行区","宝山区","嘉定区","浦东新区","金山区","松江区",
                    "青浦区","奉贤区","崇明区"
                )

                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    DropdownFilter(
                        label = "学科",
                        options = subjects,
                        selected = uiState.selectedSubject,
                        onSelected = { viewModel.setSubject(it) },
                        leadingIcon = { Icon(Icons.Default.Star, contentDescription = null) }
                    )
                    DropdownFilter(
                        label = "性别",
                        options = genders,
                        selected = uiState.selectedGender,
                        onSelected = { viewModel.setGender(it) },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) }
                    )
                    DropdownFilter(
                        label = "区域",
                        options = regions,
                        selected = uiState.selectedRegion,
                        onSelected = { viewModel.setRegion(it) },
                        leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null) }
                    )
                }
            }
        }

        // Teachers List
        Text(
            text = "共 ${uiState.teachers.size} 位老师",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (uiState.teachers.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "暂无推荐老师",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.teachers) { t ->
                    // 提取科目与性别标签
                    val subjects = t.subjects.joinToString("、") { it.displayName }
                    val genderLabel = when (t.gender) {
                        com.seecolab.istudy.data.model.Gender.MALE -> "男"
                        com.seecolab.istudy.data.model.Gender.FEMALE -> "女"
                        else -> "未知"
                    }
                    // 从 description 中拿到后端 user_id（由仓库映射时写入）
                    val userId = t.description
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                if (userId.isNotEmpty()) {
                                    onNavigateToTeacherDetail(userId)
                                }
                            },
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = t.name,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "性别: $genderLabel    科目: $subjects\n地区: ${t.location}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DropdownFilter(
    label: String,
    options: List<String>,
    selected: String?,
    onSelected: (String?) -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null
) {
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
            label = { Text(label) },
            leadingIcon = leadingIcon,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            // 清空选项
            DropdownMenuItem(
                text = { Text("全部") },
                onClick = {
                    onSelected(null)
                    expanded = false
                }
            )
            options.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        onSelected(item)
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeacherCard(
    teacher: Teacher,
    onBookClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
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
                        text = teacher.name,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    
                    Text(
                        text = "科目: ${teacher.subjects.joinToString(", ") { it.displayName }}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Text(
                        text = "地区: ${teacher.location}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Text(
                        text = "经验: ${teacher.experience}年",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.secondary
                        )
                        Text(
                            text = String.format("%.1f", teacher.rating),
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                        
                        Spacer(modifier = Modifier.weight(1f))
                        
                        Text(
                            text = "¥${teacher.hourlyRate}/小时",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = teacher.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = onBookClick,
                    enabled = teacher.isAvailable
                ) {
                    Text(stringResource(R.string.book_appointment))
                }
            }
        }
    }
}