package com.seecolab.istudy.ui.screens.upload

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.view.PreviewView
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.platform.LocalLifecycleOwner
import coil.compose.AsyncImage
import com.seecolab.istudy.ui.viewmodel.UploadViewModel
import com.seecolab.istudy.utils.CameraManager
import com.seecolab.istudy.utils.rememberCameraManager
import java.util.concurrent.Executors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadScreen(
    viewModel: UploadViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {},
    onNavigateToResults: (uploadResult: com.seecolab.istudy.data.model.StudentWorkResponse, imageUris: List<Uri>) -> Unit = { _, _ -> },
    selectedStudent: com.seecolab.istudy.data.model.StudentByRoleData? = null
) {
    val uiState by viewModel.uiState.collectAsState()
    val selectedImageUri by viewModel.selectedImageUri.collectAsState()
    val selectedImageUris by viewModel.selectedImageUris.collectAsState()
    val showCamera by viewModel.showCamera.collectAsState()
    val showGallery by viewModel.showGallery.collectAsState()
    // 完成时间（分钟）输入状态
    var completionMinutes by remember { mutableStateOf("") }

    val context = LocalContext.current
    
    // Navigate to results when upload completes successfully
    LaunchedEffect(uiState.uploadResult) {
        uiState.uploadResult?.let { result ->
            onNavigateToResults(result, selectedImageUris)
        }
    }
    
    // Camera permission launcher
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            viewModel.selectImageFromCamera()
        }
    }
    
    // Gallery launcher for multiple images (always use this one)
    val multipleGalleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetMultipleContents()
    ) { uris ->
        if (uris.isNotEmpty()) {
            viewModel.onImagesSelected(uris.filterNotNull())
        }
    }
    
    // Show camera dialog
    if (showCamera) {
        CameraDialog(
            onImageCaptured = viewModel::onImageCaptured,
            onDismiss = viewModel::dismissCamera
        )
    }
    
    // Show gallery picker for multiple images (always allow multiple selection)
    if (showGallery) {
        LaunchedEffect(showGallery) {
            multipleGalleryLauncher.launch("image/*")
            viewModel.dismissGallery()
        }
    }

    // 如果从家长区点击了“作业列表”，且传入了学生，则在此直接展示作业列表并返回
    if (selectedStudent != null) {
        ParentStudentWorkListSection(
            selectedStudent = selectedStudent
        )
        return
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Top bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text(
                text = "上传作业",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(48.dp))
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Image selection section
        if (selectedImageUris.isEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 200.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        text = "选择要上传的图片",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "可以一次选择多张图片",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // 相机（胶囊样式）
                        FilledPillButton(
                            text = "相机",
                            leadingIcon = Icons.Default.CameraAlt,
                            background = MaterialTheme.colorScheme.primary
                        ) {
                            val permission = Manifest.permission.CAMERA
                            if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
                                viewModel.selectImageFromCamera()
                            } else {
                                cameraPermissionLauncher.launch(permission)
                            }
                        }
                        // 选择图片（胶囊样式）
                        FilledPillButton(
                            text = "选择图片",
                            leadingIcon = Icons.Default.Image,
                            background = MaterialTheme.colorScheme.primary
                        ) {
                            viewModel.selectImageFromGallery()
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Text(
                        text = "您可以一次选择多张图片，或通过相机拍摄",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            // Selected images preview
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    // Show all selected images in a horizontal scrollable row
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    ) {
                        items(selectedImageUris) { uri ->
                            Box(
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            ) {
                                AsyncImage(
                                    model = uri,
                                    contentDescription = "Selected image",
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(RoundedCornerShape(8.dp)),
                                    contentScale = ContentScale.Crop
                                )
                                
                                // Remove button
                                IconButton(
                                    onClick = { viewModel.removeImage(uri) },
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .size(24.dp)
                                        .background(
                                            MaterialTheme.colorScheme.error,
                                            RoundedCornerShape(12.dp)
                                        )
                                ) {
                                    Icon(
                                        Icons.Default.Close,
                                        contentDescription = "Remove image",
                                        modifier = Modifier.size(16.dp),
                                        tint = MaterialTheme.colorScheme.onError
                                    )
                                }
                            }
                        }
                    }
                    
                    Text(
                        text = "${selectedImageUris.size} 张图片已选择",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    
                    Column {
                        // Clear button
                        Button(
                            onClick = { viewModel.clearAllImages() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(44.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error
                            )
                        ) {
                            Text(
                                text = "🗑️ 清空所有图片",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        // Add more button
                        Button(
                            onClick = { viewModel.selectImageFromGallery() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(44.dp)
                        ) {
                            Text(
                                text = "➕ 添加更多图片",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // 完成时间（分钟）输入框
                        OutlinedTextField(
                            value = completionMinutes,
                            onValueChange = { newValue ->
                                // 仅保留数字
                                completionMinutes = newValue.filter { it.isDigit() }
                            },
                            label = { Text("完成时间（分钟）") },
                            placeholder = { Text("请输入分钟数，如 30") },
                            singleLine = true,
                            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier
                                .fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        
                        // Upload button
                        Button(
                            onClick = {
                                val minutes = completionMinutes.toIntOrNull() ?: 0
                                viewModel.uploadImages(minutes)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            enabled = !uiState.isUploading && !uiState.isCancelling,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            if (uiState.isUploading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    strokeWidth = 2.dp
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = if (uiState.isCancelling) "取消中..." else "上传中...",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            } else {
                                Text(
                                    text = "☁️ 上传所有图片",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                        
                        // Cancel button - only show during upload
                        if (uiState.isUploading && !uiState.isCancelling) {
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Button(
                                onClick = { viewModel.cancelUpload() },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(44.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.error
                                )
                            ) {
                                Text(
                                    text = "✕ 取消上传",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onError
                                )
                            }
                        }
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Upload progress and results - Only show during upload or error
        if (uiState.isUploading || uiState.error != null) {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "上传状态",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    if (uiState.isUploading) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CircularProgressIndicator(modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = if (uiState.isCancelling) "正在取消..." else "正在上传并分析...",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                    
                    // Error handling
                    uiState.error?.let { error ->
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.errorContainer
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.Error,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.error
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = error,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onErrorContainer
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FilledPillButton(
    text: String,
    leadingIcon: ImageVector,
    background: Color,
    contentColor: Color = Color.White,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(background)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null,
                tint = contentColor
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color = contentColor,
                maxLines = 1
            )
        }
    }
}

@Composable
fun CameraDialog(
    onImageCaptured: (Uri) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraManager = rememberCameraManager(context)
    val executor = remember { Executors.newSingleThreadExecutor() }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("取消")
            }
        },
        title = { Text("相机") },
        text = {
            Column {
                AndroidView(
                    factory = { ctx ->
                        PreviewView(ctx).apply {
                            cameraManager.setupCamera(lifecycleOwner, this, executor)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Button(
                    onClick = {
                        cameraManager.captureImage(
                            onImageCaptured = onImageCaptured,
                            onError = { /* Handle error */ }
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.CameraAlt, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("拍照")
                }
            }
        }
    )
    
    DisposableEffect(Unit) {
        onDispose {
            cameraManager.unbind()
            executor.shutdown()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParentStudentWorkListSection(
    selectedStudent: com.seecolab.istudy.data.model.StudentByRoleData,
    onWorkItemClick: (com.seecolab.istudy.data.model.StudentWorkListItem) -> Unit = {},
    viewModel: com.seecolab.istudy.ui.viewmodel.ParentZoneViewModel = androidx.hilt.navigation.compose.hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // 进入时根据传入学生触发加载（访问后端）
    LaunchedEffect(selectedStudent.user_id) {
        viewModel.selectStudent(selectedStudent)
    }

    // 筛选状态
    var selectedSubject by remember { mutableStateOf<String?>(null) }
    var startDate by remember { mutableStateOf("") }   // YYYY-MM-DD
    var endDate by remember { mutableStateOf("") }     // YYYY-MM-DD
    var scoreBelow by remember { mutableStateOf("") }  // 低于多少分
    var showDatePicker by remember { mutableStateOf(false) }
    var isStartPicker by remember { mutableStateOf(true) }

    // 本地筛选（基于已从后端拉取的 uiState.studentWorkList）
    val filteredList = remember(uiState.studentWorkList, selectedSubject, startDate, endDate, scoreBelow) {
        val dateParser = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", java.util.Locale.getDefault())
        val startMillis = startDate.takeIf { it.length == 10 }?.let {
            java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).parse(it)?.time
        }
        val endMillis = endDate.takeIf { it.length == 10 }?.let {
            // 包含当天：加一日减一毫秒可按需处理，这里直接到当天 23:59:59 的比较由接口数据决定，先简单比较日期
            java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).parse(it)?.time
        }
        val scoreMax = scoreBelow.toFloatOrNull()

        uiState.studentWorkList.filter { item ->
            val subjectOk = selectedSubject?.let { sub -> (item.subject ?: "").contains(sub) } ?: true

            val dateOk = try {
                val millis = dateParser.parse(item.create_time)?.time
                val afterStart = startMillis?.let { millis != null && millis >= it } ?: true
                val beforeEnd = endMillis?.let { millis != null && millis <= it } ?: true
                afterStart && beforeEnd
            } catch (_: Exception) { true }

            val scoreOk = scoreMax?.let { max ->
                item.score?.let { it.toFloat() <= max } ?: true
            } ?: true

            subjectOk && dateOk && scoreOk
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // 标题
        Text(
            text = "${selectedStudent.username} 的作业列表",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 筛选区
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "筛选",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(12.dp))

                // 学科下拉
                ParentSubjectDropdown(
                    selected = selectedSubject,
                    onSelected = { selectedSubject = it }
                )

                Spacer(modifier = Modifier.height(12.dp))

                // 日期区间
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = startDate,
                        onValueChange = { startDate = it },
                        label = { Text("开始日期") },
                        placeholder = { Text("YYYY-MM-DD") },
                        trailingIcon = {
                            IconButton(onClick = { isStartPicker = true; showDatePicker = true }) {
                                Icon(Icons.Default.DateRange, contentDescription = null)
                            }
                        },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = endDate,
                        onValueChange = { endDate = it },
                        label = { Text("结束日期") },
                        placeholder = { Text("YYYY-MM-DD") },
                        trailingIcon = {
                            IconButton(onClick = { isStartPicker = false; showDatePicker = true }) {
                                Icon(Icons.Default.DateRange, contentDescription = null)
                            }
                        },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // 考试成绩 低于多少分
                OutlinedTextField(
                    value = scoreBelow,
                    onValueChange = { scoreBelow = it.filter { ch -> ch.isDigit() || ch == '.' } },
                    label = { Text("成绩低于（分）") },
                    placeholder = { Text("如 60") },
                    singleLine = true,
                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = {
                            // 若需要服务端筛选，这里可调用 viewModel 的检索方法；当前为本地筛选，按钮仅触发重组即可
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.Search, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("搜索")
                    }
                    OutlinedButton(
                        onClick = {
                            selectedSubject = null
                            startDate = ""
                            endDate = ""
                            scoreBelow = ""
                            // 可按需触发刷新：viewModel.selectStudent(selectedStudent)
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("清除")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 列表区
        if (uiState.isLoadingWorkList) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (uiState.studentWorkList.isEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(Icons.Default.Assignment, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("暂无作业记录", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        } else {
            androidx.compose.foundation.lazy.LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(filteredList) { workItem ->
                    com.seecolab.istudy.ui.screens.ParentWorkListItem(
                        workItem = workItem,
                        isDeleting = uiState.isDeleting,
                        onClick = { onWorkItemClick(workItem) },
                        onDeleteClick = { viewModel.deleteStudentWork(workItem) }
                    )
                }
            }
        }
    }

    // 日期选择器
    if (showDatePicker) {
        ParentDatePickerDialog(
            isStartDate = isStartPicker,
            onDateSelected = { dateString ->
                if (isStartPicker) startDate = dateString else endDate = dateString
                showDatePicker = false
            },
            onDismiss = { showDatePicker = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ParentSubjectDropdown(
    selected: String?,
    onSelected: (String?) -> Unit
) {
    val subjects = listOf("语文","数学","英语","物理","化学","生物","政治","地理","科学","综合")
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
            DropdownMenuItem(text = { Text("全部") }, onClick = { onSelected(null); expanded = false })
            subjects.forEach { s ->
                DropdownMenuItem(text = { Text(s) }, onClick = { onSelected(s); expanded = false })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ParentDatePickerDialog(
    isStartDate: Boolean,
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = System.currentTimeMillis())
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                datePickerState.selectedDateMillis?.let { millis ->
                    val formatter = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
                    val dateString = formatter.format(java.util.Date(millis))
                    onDateSelected(dateString)
                }
            }) { Text("确定") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("取消") } }
    ) {
        DatePicker(
            state = datePickerState,
            title = { Text(text = if (isStartDate) "选择开始日期" else "选择结束日期", modifier = Modifier.padding(16.dp)) }
        )
    }
}