package com.wswon.picker

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.MediaStore

class ImageLoadManager(private val context: Context) {

    @SuppressLint("InlinedApi")
    fun getFilePaths(): Set<Pair<String, Uri>> {

        val uriExternal: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val orderBy = MediaStore.Images.Media.DATE_TAKEN
        val imageSet = mutableSetOf<Pair<String, Uri>>()
        val projection = arrayOf(
            MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media._ID
        )

        val cursor = context.contentResolver
            .query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                "$orderBy DESC"
            )

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val bucketColumn =
                    cursor.getColumnIndex(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME)

                val uriName =
                    cursor.getColumnIndex(MediaStore.Images.Media._ID)

                do {
                    val bucket = cursor.getString(bucketColumn)
                    val imageId = cursor.getLong(uriName)
                    val imageUri = Uri.withAppendedPath(uriExternal, "" + imageId)
                    imageSet.add(bucket to imageUri)
                } while (cursor.moveToNext())
            }
        }
        cursor?.close()
        return imageSet
    }

}