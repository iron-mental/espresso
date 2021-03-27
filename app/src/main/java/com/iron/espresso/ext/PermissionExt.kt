package com.iron.espresso.ext

import android.Manifest
import android.content.Context
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.iron.espresso.R

inline fun checkReadStoragePermission(context: Context, crossinline success: (Boolean) -> Unit) {
    TedPermission.with(context)
        .setPermissionListener(
            object : PermissionListener {
                override fun onPermissionGranted() {
                    success(true)
                }

                override fun onPermissionDenied(deniedPermissions: List<String>) {

                }
            }
        )
        .setRationaleTitle(R.string.read_storage_permission_title)
        .setRationaleMessage(R.string.read_storage_permission_contents) // "we need permission for read contact and find your location"
        .setPermissions(
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        .check()
}