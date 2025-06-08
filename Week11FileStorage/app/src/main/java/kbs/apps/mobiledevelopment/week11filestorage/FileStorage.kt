package kbs.apps.mobiledevelopment.week11filestorage

import android.content.Context

import java.io.File

import java.io.FileOutputStream

import java.io.IOException

class FileStorage{
    fun writeToFileInternal(context: Context, fileName: String, data: String) {
        try {
            // Open a file in internal storage
            val file = File(context.filesDir, fileName)
            // Create a FileOutputStream and write data to the file
            val fileOutputStream = FileOutputStream(file)
            fileOutputStream.write(data.toByteArray())
            // Close the stream
            fileOutputStream.close ()
        } catch (e: IOException) {
            e. printStackTrace()
        }
    }
    }

}