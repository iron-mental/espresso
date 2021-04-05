package com.wswon.picker

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wswon.picker.common.BaseViewModel
import com.wswon.picker.ext.plusAssign
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class ImageDetailViewModel @ViewModelInject constructor(private val imageLoadManager: ImageLoadManager) :
    BaseViewModel() {

    private val _imageDetailItemList = MutableLiveData<List<ImageDetailItem>>()
    val imageDetailItemList: LiveData<List<ImageDetailItem>>
        get() = _imageDetailItemList

    fun getImageUri(folderName: String = "") {
        compositeDisposable += Single.just(0)
            .subscribeOn(Schedulers.io())
            .map {
                val imageSet = imageLoadManager.getFilePaths()
                imageSet
                    .filter {
                        if (folderName.isNotEmpty()) {
                            it.first == folderName
                        } else {
                            true
                        }
                    }
                    .map {
                        ImageDetailItem(it.second)
                    }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ imageSet ->
                _imageDetailItemList.value = imageSet
            }, {

            })
    }
}