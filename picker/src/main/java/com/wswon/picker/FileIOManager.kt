package com.wswon.picker

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File

class FileIOManager(private val context: Context) {

    fun readFileToUri(fileName: String): Uri? {
        try {
            val findFile = context.filesDir.listFiles()?.find { it.name == fileName }
            return findFile?.toUri()
        } catch (e: Exception) {

        }
        return null
    }


    fun deleteAllFile(memoIdentifier: String) {
        try {
            context.filesDir.listFiles()?.forEach {
                if (it.name.contains(memoIdentifier)) {
                    it.delete()
                }
            }
        } catch (e: Exception) {

        }
    }

    fun deleteFile(fileName: String) {
        try {
            context.filesDir.listFiles()?.forEach {
                if (it.name == fileName) {
                    it.delete()
                    return
                }
            }
        } catch (e: Exception) {

        }
    }

    fun deleteAllExternalFile() {
        try {
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                ?.listFiles()
                ?.forEach {
                    it.delete()
                }

        } catch (e: Exception) {

        }
    }


    fun makeFileList(imageUriList: List<Pair<Int, Uri?>>): List<Observable<Pair<Int, File>>> {
        return imageUriList.mapNotNull { pair ->
            if (pair.second == null) {
                return@mapNotNull null
            } else {
                Observable.just(1)
                    .map {
                        pair.first to Glide.with(context)
                            .asFile()
                            .load(pair.second)
                            .submit()
                            .get()
                    }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
            }
        }
    }

    fun writeFileOnInternalStorage(file: File, saveFileName: String) {
        try {
            val fos = context.openFileOutput(saveFileName, Context.MODE_PRIVATE)
            fos.write(file.readBytes())
            fos.flush()
            fos.close()
        } catch (e: Exception) {
        }
    }
}