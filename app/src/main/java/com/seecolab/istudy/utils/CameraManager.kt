package com.seecolab.istudy.utils

import android.content.Context
import android.net.Uri
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executor

class CameraManager(private val context: Context) {
    
    private var imageCapture: ImageCapture? = null
    private var preview: Preview? = null
    private var camera: Camera? = null
    
    fun setupCamera(
        lifecycleOwner: LifecycleOwner,
        previewView: PreviewView,
        executor: Executor
    ) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            
            // Preview
            preview = Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }
            
            // Image capture
            imageCapture = ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build()
            
            // Select back camera as default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            
            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()
                
                // Bind use cases to camera
                camera = cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    preview,
                    imageCapture
                )
            } catch (exc: Exception) {
                exc.printStackTrace()
            }
        }, executor)
    }
    
    fun captureImage(
        onImageCaptured: (Uri) -> Unit,
        onError: (Exception) -> Unit
    ) {
        val imageCapture = this.imageCapture ?: return
        
        // Create time-stamped output file to hold the image
        val name = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", Locale.US)
            .format(System.currentTimeMillis())
        val contentValues = android.content.ContentValues().apply {
            put(android.provider.MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(android.provider.MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.P) {
                put(android.provider.MediaStore.Images.Media.RELATIVE_PATH, "Pictures/iStudy")
            }
        }
        
        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions.Builder(
            context.contentResolver,
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        ).build()
        
        // Set up image capture listener
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exception: ImageCaptureException) {
                    onError(exception)
                }
                
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    output.savedUri?.let { onImageCaptured(it) }
                }
            }
        )
    }
    
    fun getFlashMode(): Int = imageCapture?.flashMode ?: ImageCapture.FLASH_MODE_OFF
    
    fun setFlashMode(flashMode: Int) {
        imageCapture?.flashMode = flashMode
    }
    
    fun hasFlash(): Boolean = camera?.cameraInfo?.hasFlashUnit() ?: false
    
    fun unbind() {
        val cameraProvider = ProcessCameraProvider.getInstance(context)
        try {
            cameraProvider.get().unbindAll()
        } catch (e: Exception) {
            // Handle exception
        }
    }
}

@Composable
fun rememberCameraManager(context: Context): CameraManager {
    return remember { CameraManager(context) }
}