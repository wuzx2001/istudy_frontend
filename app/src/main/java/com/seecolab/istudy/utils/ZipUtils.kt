package com.seecolab.istudy.utils

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

object ZipUtils {
    
    /**
     * Creates a zip file containing all the provided image URIs
     * @param context Application context
     * @param imageUris List of image URIs to include in the zip
     * @return File object representing the created zip file
     */
    fun createZipFromImages(context: Context, imageUris: List<Uri>): File {
        val zipFile = File(context.cacheDir, "multiple_images_${System.currentTimeMillis()}.zip")
        
        ZipOutputStream(FileOutputStream(zipFile)).use { zipOutputStream ->
            imageUris.forEachIndexed { index, uri ->
                val imageFile = ImageUtils.uriToFile(context, uri)
                val entryName = "image_${index + 1}_${imageFile.name}"
                
                // Add file to zip
                FileInputStream(imageFile).use { fis ->
                    zipOutputStream.putNextEntry(ZipEntry(entryName))
                    fis.copyTo(zipOutputStream)
                    zipOutputStream.closeEntry()
                }
                
                // Delete temporary file
                imageFile.delete()
            }
        }
        
        return zipFile
    }
    
    /**
     * Creates a zip file containing all the provided files
     * @param context Application context
     * @param files List of files to include in the zip
     * @return File object representing the created zip file
     */
    fun createZipFromFiles(context: Context, files: List<File>): File {
        val zipFile = File(context.cacheDir, "multiple_files_${System.currentTimeMillis()}.zip")
        
        ZipOutputStream(FileOutputStream(zipFile)).use { zipOutputStream ->
            files.forEachIndexed { index, file ->
                val entryName = "file_${index + 1}_${file.name}"
                
                // Add file to zip
                FileInputStream(file).use { fis ->
                    zipOutputStream.putNextEntry(ZipEntry(entryName))
                    fis.copyTo(zipOutputStream)
                    zipOutputStream.closeEntry()
                }
            }
        }
        
        return zipFile
    }
}