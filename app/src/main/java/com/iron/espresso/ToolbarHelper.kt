package com.iron.espresso

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar

class ToolbarHelper(activity: AppCompatActivity, rootView: ViewGroup) {
    private var toolbar: MaterialToolbar
    private var actionBar: ActionBar?

    init {
        val toolbar = LayoutInflater.from(activity)
            .inflate(R.layout.view_toolbar, rootView, false) as MaterialToolbar

        this.toolbar = toolbar
        activity.setSupportActionBar(toolbar)
        actionBar = activity.supportActionBar

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

    fun setCustomView(view: View) {
        setCustomView(view, false)
    }

    fun setCustomView(view: View, relativeHeight: Boolean) {
        toolbar.run {
            addView(view)
            setContentInsetsAbsolute(0, 0)
            if (relativeHeight) {
                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            }
        }
    }
}