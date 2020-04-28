package com.ss.itask.util

import android.graphics.Bitmap
import java.io.File

class FileUtils {
     fun File.writeBitmap(bitmap: Bitmap, format: Bitmap.CompressFormat, quality: Int) {
        outputStream().use { out ->
            bitmap.compress(format, quality, out)
            out.flush()
        }
    }
}