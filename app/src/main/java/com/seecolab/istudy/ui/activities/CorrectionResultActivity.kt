package com.seecolab.istudy.ui.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.google.gson.Gson
import com.seecolab.istudy.data.model.StudentWorkResponse
import com.seecolab.istudy.ui.screens.result.MathResultDisplayScreen
import com.seecolab.istudy.ui.theme.IStudyTheme
import com.seecolab.istudy.ui.viewmodel.CorrectionResultViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CorrectionResultActivity : ComponentActivity() {
    
    private val viewModel: CorrectionResultViewModel by viewModels()
    
    companion object {
        private const val EXTRA_STREAMING_CONTENT = "extra_streaming_content"
        private const val EXTRA_RESULT_JSON = "extra_result_json"
        private const val EXTRA_IMAGE_URIS = "extra_image_uris"
        private const val EXTRA_IS_STREAMING = "extra_is_streaming"
        
        fun createIntent(
            context: Context,
            streamingContent: String,
            result: StudentWorkResponse?,
            imageUris: List<Uri>,
            isStreamingActive: Boolean = false
        ): Intent {
            return Intent(context, CorrectionResultActivity::class.java).apply {
                putExtra(EXTRA_STREAMING_CONTENT, streamingContent)
                putExtra(EXTRA_IS_STREAMING, isStreamingActive)
                // Convert result to JSON string to avoid Parcelable issues
                result?.let {
                    val gson = Gson()
                    putExtra(EXTRA_RESULT_JSON, gson.toJson(it))
                }
                putParcelableArrayListExtra(EXTRA_IMAGE_URIS, ArrayList(imageUris))
            }
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val streamingContent = intent.getStringExtra(EXTRA_STREAMING_CONTENT) ?: ""
        val isStreamingActive = intent.getBooleanExtra(EXTRA_IS_STREAMING, false)
        android.util.Log.d("CorrectionResultActivity", "Received streaming content: ${streamingContent.length} chars, streaming active: $isStreamingActive")
        
        // Parse result from JSON string
        val result = intent.getStringExtra(EXTRA_RESULT_JSON)?.let { json ->
            android.util.Log.d("CorrectionResultActivity", "Received result JSON: $json")
            try {
                Gson().fromJson(json, StudentWorkResponse::class.java)
            } catch (e: Exception) {
                android.util.Log.e("CorrectionResultActivity", "Error parsing result JSON", e)
                null
            }
        }
        
        val imageUris = intent.getParcelableArrayListExtra<Uri>(EXTRA_IMAGE_URIS) ?: emptyList()
        android.util.Log.d("CorrectionResultActivity", "Received ${imageUris.size} image URIs")
        
        setContent {
            IStudyTheme {
                val uiState by viewModel.uiState.collectAsState()
                
                // Initialize data when activity is created
                LaunchedEffect(Unit) {
                    viewModel.initializeData(result, imageUris)
                }
                
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MathResultDisplayScreen(
                        uiState = uiState,
                        onNavigateBack = { finish() },
                        onShowImages = { viewModel.showImageDialog(0) }
                    )
                }
                
                // Image dialog overlay
                if (uiState.showImageDialog && uiState.imageUris.isNotEmpty()) {
                    com.seecolab.istudy.ui.screens.result.ImageViewerDialog(
                        imageUris = uiState.imageUris,
                        initialIndex = uiState.selectedImageIndex,
                        onDismiss = { viewModel.hideImageDialog() },
                        onImageSelected = { viewModel.selectImage(it) }
                    )
                }
            }
        }
    }
}