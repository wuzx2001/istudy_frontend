package com.seecolab.istudy.ui.screens.learning

import android.Manifest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.seecolab.istudy.R
import com.seecolab.istudy.ui.viewmodel.HomeworkCorrectionViewModel
import com.seecolab.istudy.utils.CameraManager
import com.seecolab.istudy.utils.rememberCameraManager

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun HomeworkCorrectionScreen(
    navController: NavController,
    viewModel: HomeworkCorrectionViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraManager = rememberCameraManager(context)
    
    var showCamera by remember { mutableStateOf(false) }
    var capturedImageUri by remember { mutableStateOf<Uri?>(null) }
    var isAnalyzing by remember { mutableStateOf(false) }
    
    val uiState by viewModel.uiState.collectAsState()
    
    // Camera permission
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    
    // Image picker launcher
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            capturedImageUri = it
            showCamera = false
        }
    }
    if (showCamera && cameraPermissionState.status.isGranted) {
        CameraScreen(
            cameraManager = cameraManager,
            lifecycleOwner = lifecycleOwner,
            onImageCaptured = { uri ->
                capturedImageUri = uri
                showCamera = false
            },
            onClose = { showCamera = false }
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "返回")
                }
                
                Text(
                    text = stringResource(R.string.homework_correction),
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            // Show captured image if available
            capturedImageUri?.let { uri ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "已拍摄的作业",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        
                        Image(
                            painter = rememberAsyncImagePainter(uri),
                            contentDescription = "Captured homework",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(
                                onClick = {
                                    isAnalyzing = true
                                    viewModel.analyzeHomework(uri)
                                },
                                enabled = !isAnalyzing
                            ) {
                                if (isAnalyzing) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(16.dp),
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                }
                                Text(if (isAnalyzing) "分析中..." else "开始分析")
                            }
                            
                            OutlinedButton(
                                onClick = { capturedImageUri = null }
                            ) {
                                Text("重新拍摄")
                            }
                        }
                    }
                }
            }

            // Camera capture section
            if (capturedImageUri == null) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Text(
                            text = "拍照上传作业",
                            style = MaterialTheme.typography.titleLarge
                        )
                        
                        Text(
                            text = "请拍摄清晰的作业照片，AI将自动识别并批改",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                        
                        Spacer(modifier = Modifier.height(20.dp))
                        
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Button(
                                onClick = {
                                    if (cameraPermissionState.status.isGranted) {
                                        showCamera = true
                                    } else {
                                        cameraPermissionState.launchPermissionRequest()
                                    }
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.Edit, contentDescription = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("拍摄")
                            }
                            
                            OutlinedButton(
                                onClick = { imagePickerLauncher.launch("image/*") },
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.Star, contentDescription = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("相册")
                            }
                        }
                    }
                }
            }

            // Analysis results
            uiState.analysisResult?.let { result ->
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "分析结果",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        
                        // Overall score
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "总体得分",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "${result.overallScore.toInt()}%",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Problem analysis
                        result.problems.forEach { problem ->
                            ProblemAnalysisCard(problem = problem)
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        
                        // Suggestions
                        if (result.suggestions.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "学习建议",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            
                            result.suggestions.forEach { suggestion ->
                                Row(
                                    modifier = Modifier.padding(vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "• $suggestion",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "功能说明",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            val features = listOf(
                "AI智能识别题目和答案",
                "自动批改并给出正确答案",
                "分析错误原因和知识点",
                "生成个性化学习建议",
                "推荐相关练习题目"
            )

            features.forEach { feature ->
                Row(
                    modifier = Modifier.padding(vertical = 4.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    
                    Text(
                        text = feature,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CameraScreen(
    cameraManager: CameraManager,
    lifecycleOwner: LifecycleOwner,
    onImageCaptured: (Uri) -> Unit,
    onClose: () -> Unit
) {
    val context = LocalContext.current
    
    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { ctx ->
                PreviewView(ctx).apply {
                    cameraManager.setupCamera(
                        lifecycleOwner,
                        this,
                        ContextCompat.getMainExecutor(ctx)
                    )
                }
            },
            modifier = Modifier.fillMaxSize()
        )
        
        // Camera controls
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(32.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Close button
            IconButton(
                onClick = onClose,
                modifier = Modifier
                    .size(60.dp)
                    .background(
                        MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                        androidx.compose.foundation.shape.CircleShape
                    )
            ) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "关闭",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
            
            Spacer(modifier = Modifier.width(32.dp))
            
            // Capture button
            IconButton(
                onClick = {
                    cameraManager.captureImage(
                        onImageCaptured = onImageCaptured,
                        onError = { /* Handle error */ }
                    )
                },
                modifier = Modifier
                    .size(80.dp)
                    .background(
                        MaterialTheme.colorScheme.primary,
                        androidx.compose.foundation.shape.CircleShape
                    )
            ) {
                Icon(
                    Icons.Default.Edit,
                    contentDescription = "拍摄",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}

@Composable
fun ProblemAnalysisCard(
    problem: com.seecolab.istudy.data.api.ProblemAnalysis
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (problem.isCorrect) 
                MaterialTheme.colorScheme.primaryContainer
            else 
                MaterialTheme.colorScheme.errorContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "题目 ${problem.questionNumber}",
                    style = MaterialTheme.typography.titleSmall
                )
                Icon(
                    imageVector = if (problem.isCorrect) Icons.Default.CheckCircle else Icons.Default.Close,
                    contentDescription = null,
                    tint = if (problem.isCorrect) 
                        MaterialTheme.colorScheme.primary
                    else 
                        MaterialTheme.colorScheme.error
                )
            }
            
            if (!problem.isCorrect) {
                Text(
                    text = "你的答案: ${problem.studentAnswer}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
                Text(
                    text = "正确答案: ${problem.correctAnswer}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            
            Text(
                text = problem.explanation,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}