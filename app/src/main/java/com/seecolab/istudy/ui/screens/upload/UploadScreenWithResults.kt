package com.seecolab.istudy.ui.screens.upload

import android.net.Uri
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.seecolab.istudy.data.model.StudentWorkResponse
import com.seecolab.istudy.ui.viewmodel.UploadViewModel

/**
 * Example of how to integrate the new ResultDetailScreen with navigation
 * This shows the usage pattern for the enhanced upload flow
 */

@Composable
fun UploadScreenWithResults(
    navController: NavController? = null,
    onNavigateBack: () -> Unit = {},
    uploadViewModel: UploadViewModel = hiltViewModel()
) {
    var showResultsScreen by remember { mutableStateOf(false) }
    var currentResult by remember { mutableStateOf<StudentWorkResponse?>(null) }
    var currentImageUris by remember { mutableStateOf<List<Uri>>(emptyList()) }
    
    if (showResultsScreen && currentResult != null) {
        // Show the dedicated results screen using UploadResultScreen
        UploadResultScreen(
            uploadResult = currentResult!!,
            imageUris = currentImageUris,
            onNavigateBack = {
                // Reset everything after successful upload when going back
                uploadViewModel.resetAfterNavigation()
                // Go back to upload screen and clear the results
                showResultsScreen = false
                currentResult = null
                currentImageUris = emptyList()
            }
        )
    } else {
        // Show the upload screen
        UploadScreen(
            viewModel = uploadViewModel,
            onNavigateBack = onNavigateBack,
            onNavigateToResults = { uploadResult, imageUris ->
                currentResult = uploadResult
                currentImageUris = imageUris
                showResultsScreen = true
            }
        )
    }
}

/**
 * Alternative navigation using NavController
 * Add this to your navigation graph:
 * 
 * composable("upload") {
 *     UploadScreen(
 *         onNavigateBack = { navController.navigateUp() },
 *         onNavigateToResults = { uploadResult, imageUris ->
 *             // Store data in navigation arguments or ViewModel
 *             navController.navigate("results")
 *         }
 *     )
 * }
 * 
 * composable("results") {
 *     UploadResultScreen(
 *         uploadResult = // get from arguments or ViewModel,
 *         imageUris = // get from arguments or ViewModel,
 *         onNavigateBack = { navController.navigateUp() }
 *     )
 * }
 */