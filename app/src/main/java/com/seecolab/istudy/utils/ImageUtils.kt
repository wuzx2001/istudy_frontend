package com.seecolab.istudy.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

object ImageUtils {
    
    fun uriToFile(context: Context, uri: Uri): File {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val file = File(context.cacheDir, "temp_image_${System.currentTimeMillis()}.jpg")
        
        inputStream?.use { input ->
            FileOutputStream(file).use { output ->
                input.copyTo(output)
            }
        }
        
        return file
    }
    
    fun uriToBitmap(context: Context, uri: Uri): Bitmap? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    
    fun compressImage(context: Context, uri: Uri, maxWidth: Int = 1024, maxHeight: Int = 1024): File {
        val bitmap = uriToBitmap(context, uri)
        val compressedBitmap = bitmap?.let { resizeBitmap(it, maxWidth, maxHeight) }
        
        val file = File(context.cacheDir, "compressed_${System.currentTimeMillis()}.jpg")
        FileOutputStream(file).use { out ->
            compressedBitmap?.compress(Bitmap.CompressFormat.JPEG, 80, out)
        }
        
        return file
    }
    
    private fun resizeBitmap(bitmap: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        
        val ratio = Math.min(maxWidth.toFloat() / width, maxHeight.toFloat() / height)
        
        val newWidth = (width * ratio).toInt()
        val newHeight = (height * ratio).toInt()
        
        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, false)
    }
    
    fun getFileSize(file: File): Long {
        return file.length()
    }
    
    fun getFileSizeInMB(file: File): Double {
        return getFileSize(file) / (1024.0 * 1024.0)
    }
}