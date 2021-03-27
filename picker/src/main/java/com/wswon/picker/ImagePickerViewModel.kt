package com.wswon.picker

import android.net.Uri
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wswon.picker.common.BaseViewModel
import com.wswon.picker.ext.plusAssign
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class ImagePickerViewModel @ViewModelInject constructor(private val imageLoadManager: ImageLoadManager) : BaseViewModel() {

    private val allImgList = mutableSetOf<Pair<String, Uri>>()

    private val _selectorItemList = MutableLiveData<List<ImagePickerSelectorItem>>()
    val selectorItemList: LiveData<List<ImagePickerSelectorItem>>
        get() = _selectorItemList

    private val _progressState = MutableLiveData(true)
    val progressState: LiveData<Boolean>
        get() = _progressState

    private val _folderNames = MutableLiveData<List<String>>()
    val folderNames: LiveData<List<String>>
        get() = _folderNames

    private val _selectedPositionList = MutableLiveData<List<Int>>()
    val selectedPositionList: LiveData<List<Int>>
        get() = _selectedPositionList

    private val _loadFailedItemPosition = MutableLiveData<Int>()
    val loadFailedItemPosition: LiveData<Int>
        get() = _loadFailedItemPosition

    private val imgLoadError: (uri: Uri) -> Unit = { uri ->
        val findIndex = selectorItemList.value?.indexOfFirst {
            it.img == uri
        }
        if (findIndex != -1 && findIndex != null) {
            _loadFailedItemPosition.value = findIndex
        }
    }

    fun loadImageUri() {
        compositeDisposable += Single.just(0)
            .subscribeOn(Schedulers.io())
            .map {
                val imageSet = imageLoadManager.getFilePaths()

                allImgList.addAll(imageSet)

                val folderNames = imageSet.map { it.first }.toSet()

                val countFolderNames = folderNames.map { folderName ->
                    val imgCount = imageSet.filter { it.first == folderName }.size
                    "$folderName ($imgCount)"
                }

                _folderNames.postValue(countFolderNames)

                imageSet.map {
                    ImagePickerSelectorItem(it.first, it.second, imgLoadError)
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ imageList ->
                Logger.d("$imageList")
                _selectorItemList.value = imageList
                _progressState.value = false
            }, {
                Logger.d("$it")
            })
    }

    fun changeFolder(folderName: String = "") {
        if (folderName.isEmpty()) {
            _selectorItemList.value = allImgList.map {
                ImagePickerSelectorItem(it.first, it.second, imgLoadError)
            }
            return
        }

        _selectorItemList.value = allImgList.filter {
            it.first == folderName
        }.map {
            ImagePickerSelectorItem(it.first, it.second, imgLoadError)
        }

    }

    fun clearSelectedItemPosition() {
        _selectedPositionList.value = listOf()
    }


    fun addSelectedItemPosition(selectedPosition: Int) {
        _selectedPositionList.value =
//            selectedPositionList.value?.toMutableList()?.apply {
//            add(selectedPosition)
//        } ?:
            listOf(selectedPosition)
    }

    fun removeSelectedItemPosition(removePosition: Int) {
        _selectedPositionList.value = selectedPositionList.value?.toMutableList()?.apply {
            remove(removePosition)
        }
    }

    fun getItemUriList(): List<Uri>? =
        selectedPositionList.value?.mapNotNull {
            selectorItemList.value?.get(it)?.img
        }
}