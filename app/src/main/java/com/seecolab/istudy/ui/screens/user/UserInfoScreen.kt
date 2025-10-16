package com.seecolab.istudy.ui.screens.user

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.seecolab.istudy.data.model.*
import com.seecolab.istudy.ui.theme.IStudyTheme
import com.seecolab.istudy.ui.viewmodel.UserInfoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInfoScreen(
    onNavigateToAddStudent: () -> Unit,
    onNavigateToAddParent: () -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: UserInfoViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    
    // Show message snackbar
    uiState.message?.let { message ->
        LaunchedEffect(message) {
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Short
            )
            viewModel.clearMessage()
        }
    }
    
    // Show error snackbar
    uiState.error?.let { error ->
        LaunchedEffect(error) {
            snackbarHostState.showSnackbar(
                message = error,
                duration = SnackbarDuration.Long
            )
            viewModel.clearError()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("用户管理") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "返回"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.refresh() }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "刷新"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FloatingActionButton(
                    onClick = onNavigateToAddStudent,
                    containerColor = MaterialTheme.colorScheme.secondary
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "添加学生"
                    )
                }
                
                FloatingActionButton(
                    onClick = onNavigateToAddParent,
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        imageVector = Icons.Default.PersonAdd,
                        contentDescription = "添加家长"
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.05f),
                            MaterialTheme.colorScheme.background
                        )
                    )
                )
        ) {
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Parents Section
                    item {
                        SectionHeader(
                            title = "家长信息",
                            count = uiState.parents.size,
                            icon = Icons.Default.People
                        )
                    }
                    
                    if (uiState.parents.isEmpty()) {
                        item {
                            EmptyStateCard(
                                message = "暂无家长信息",
                                actionText = "添加家长",
                                onActionClick = onNavigateToAddParent
                            )
                        }
                    } else {
                        items(uiState.parents) { parent ->
                            ParentCard(
                                parent = parent,
                                onDeleteClick = { viewModel.deleteParent(parent.user_id) }
                            )
                        }
                    }
                    
                    // Students Section
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        SectionHeader(
                            title = "学生信息",
                            count = uiState.students.size,
                            icon = Icons.Default.School
                        )
                    }
                    
                    if (uiState.students.isEmpty()) {
                        item {
                            EmptyStateCard(
                                message = "暂无学生信息",
                                actionText = "添加学生",
                                onActionClick = onNavigateToAddStudent
                            )
                        }
                    } else {
                        items(uiState.students) { student ->
                            StudentCard(
                                student = student,
                                onDeleteClick = { viewModel.deleteStudent(student.user_id) }
                            )
                        }
                    }
                    
                    // Bottom spacing for FAB
                    item {
                        Spacer(modifier = Modifier.height(80.dp))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SectionHeader(
    title: String,
    count: Int,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
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
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = count.toString(),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}

@Composable
private fun ParentCard(
    parent: ParentByRoleData,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = if (parent.is_master) {
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
            )
        } else {
            CardDefaults.cardColors()
        }
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = parent.username,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.SemiBold
                            ),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        
                        if (parent.is_master) {
                            Spacer(modifier = Modifier.width(8.dp))
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = MaterialTheme.colorScheme.primary,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "主账户",
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    Text(
                        text = "手机号：${parent.telephone}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Text(
                        text = "类型：${parent.user_type}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                if (!parent.is_master) {
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "删除家长",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                } else {
                    // Show a crown icon for master users instead of delete button
                    IconButton(onClick = { /* No action for master user */ }) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "主账户",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
    
    // Only show delete dialog for non-master parents
    if (showDeleteDialog && !parent.is_master) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("确认删除") },
            text = { Text("确定要删除家长 ${parent.username} 吗？") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeleteClick()
                        showDeleteDialog = false
                    }
                ) {
                    Text("删除", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("取消")
                }
            }
        )
    }
}

@Composable
private fun StudentCard(
    student: StudentByRoleData,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
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
                        text = student.username,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    Text(
                        text = "手机号：${student.telephone}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Row {
                        Text(
                            text = "年龄：${student.age}岁",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = "性别：${student.sex}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    
                    Text(
                        text = "年级：${student.grade}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Text(
                        text = "注册时间：${student.reg_date.take(10)}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                IconButton(onClick = { showDeleteDialog = true }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "删除学生",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
    
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("确认删除") },
            text = { Text("确定要删除学生 ${student.username} 吗？") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeleteClick()
                        showDeleteDialog = false
                    }
                ) {
                    Text("删除", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("取消")
                }
            }
        )
    }
}

@Composable
private fun EmptyStateCard(
    message: String,
    actionText: String,
    onActionClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.PersonAdd,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(48.dp)
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedButton(onClick = onActionClick) {
                Text(actionText)
            }
        }
    }
}