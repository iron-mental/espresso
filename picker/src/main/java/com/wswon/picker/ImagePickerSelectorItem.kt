package com.wswon.picker

import android.net.Uri

data class ImagePickerSelectorItem(
    val folderName: String,
    val img: Uri?,
    val imgLoadError: (uri: Uri) -> Unit,
    var isSelected: Boolean = false,
    var isLoaded: Boolean = true
)