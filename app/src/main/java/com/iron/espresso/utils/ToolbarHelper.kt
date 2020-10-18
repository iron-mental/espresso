package com.iron.espresso.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.iron.espresso.R

class ToolbarHelper(activity: AppCompatActivity, rootView: ViewGroup) {
    private var toolbar: MaterialToolbar
    private var actionBar: ActionBar?

    init {
        val toolbar = LayoutInflater.from(activity)
            .inflate(R.layout.view_toolbar, rootView, false) as MaterialToolbar

        this.toolbar = toolbar
        activity.setSupportActionBar(toolbar)
        actionBar = activity.supportActionBar
        setTitle("")
        rootView.addView(toolbar)
    }

    fun setTitle(@StringRes titleResId: Int) {
        actionBar?.setTitle(titleResId)
    }

    fun setTitle(title: String) {
        actionBar?.title = title
    }

    fun setNavigationIcon(@DrawableRes iconResId: Int) {
        actionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(iconResId)
        }
    }
}