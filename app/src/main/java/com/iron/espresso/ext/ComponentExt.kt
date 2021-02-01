package com.iron.espresso.ext

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import com.iron.espresso.LoadingFragment

fun FragmentActivity.setLoading(isLoading: Boolean) {
    if (isLoading) {
        showLoading()
    } else {
        hideLoading()
    }
}

fun FragmentActivity.showLoading() {
    val loadingFragment =
        supportFragmentManager.findFragmentByTag(LoadingFragment::class.java.simpleName)

    if (loadingFragment != null) {
        supportFragmentManager.commit(allowStateLoss = true) {
            show(loadingFragment)
        }

    } else {
        supportFragmentManager.commit(allowStateLoss = true) {
            val fragment = LoadingFragment.newInstance()
            add(android.R.id.content, fragment, fragment::class.java.simpleName)
        }
    }
}

fun FragmentActivity.hideLoading() {
    val loadingFragment =
        supportFragmentManager.findFragmentByTag(LoadingFragment::class.java.simpleName)

    if (loadingFragment != null) {
        supportFragmentManager.commit(allowStateLoss = true) {
            remove(loadingFragment)
        }
    }
}

fun Fragment.setLoading(isLoading: Boolean) {
    if (isLoading) {
        showLoading()
    } else {
        hideLoading()
    }
}

fun Fragment.showLoading() {
    val loadingFragment =
        parentFragmentManager.findFragmentByTag(LoadingFragment::class.java.simpleName)

    if (loadingFragment != null) {
        parentFragmentManager.commit(allowStateLoss = true) {
            show(loadingFragment)
        }

    } else {
        parentFragmentManager.commit(allowStateLoss = true) {
            val fragment = LoadingFragment.newInstance()
            add(android.R.id.content, fragment, fragment::class.java.simpleName)
        }
    }
}

fun Fragment.hideLoading() {
    val loadingFragment =
        parentFragmentManager.findFragmentByTag(LoadingFragment::class.java.simpleName)

    if (loadingFragment != null) {
        parentFragmentManager.commit(allowStateLoss = true) {
            remove(loadingFragment)
        }
    }
}
