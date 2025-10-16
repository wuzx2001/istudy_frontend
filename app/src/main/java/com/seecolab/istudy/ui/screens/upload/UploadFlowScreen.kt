package com.seecolab.istudy.ui.screens.upload

import android.net.Uri
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.seecolab.istudy.data.model.StudentWorkResponse
import com.seecolab.istudy.ui.viewmodel.UploadViewModel

/**
 * Wrapper component that handles navigation between upload screen and results screen
 * Following the Results View Design memory specification for dedicated result views
 */
@Composable
fun UploadFlowScreen(
    onNavigateBack: () -> Unit = {},
    uploadViewModel: UploadViewModel = hiltViewModel()
) {
    var showResultsScreen by remember { mutableStateOf(false) }
    var currentUploadResult by remember { mutableStateOf<StudentWorkResponse?>(null) }
    var currentImageUris by remember { mutableStateOf<List<Uri>>(emptyList()) }
    
    if (showResultsScreen && currentUploadResult != null) {
        // Show the dedicated results screen
        UploadResultScreen(
            uploadResult = currentUploadResult!!,
            imageUris = currentImageUris,
            onNavigateBack = {
                // Clear images and reset upload state when going back
                uploadViewModel.resetAfterNavigation()
                showResultsScreen = false
                currentUploadResult = null
                currentImageUris = emptyList()
            }
        )
    } else {
        // Show the upload screen
        UploadScreen(
            viewModel = uploadViewModel,
            onNavigateBack = onNavigateBack,
            onNavigateToResults = { uploadResult, imageUris ->
                currentUploadResult = uploadResult
                currentImageUris = imageUris
                showResultsScreen = true
            }
        )
    }
}