package com.iron.espresso.ext

import android.Manifest
import android.content.Context
import com.gun0912.tedpermission.TedPermissionResult
import com.iron.espresso.R
import com.tedpark.tedpermission.rx2.TedRx2Permission
import io.reactivex.disposables.Disposable

inline fun checkReadStoragePermission(context: Context, crossinline success: (Boolean) -> Unit): Disposable {
    return TedRx2Permission.with(context)
        .setRationaleTitle(R.string.read_storage_permission_title)
        .setRationaleMessage(R.string.read_storage_permission_contents) // "we need permission for read contact and find your location"
        .setPermissions(
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        .request()
        .subscribe({ result: TedPermissionResult ->
            success(result.isGranted)
        }, {

        })
}