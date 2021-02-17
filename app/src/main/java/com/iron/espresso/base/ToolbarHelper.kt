package com.iron.espresso.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.iron.espresso.R

class ToolbarHelper(activity: AppCompatActivity, rootView: ViewGroup) :
    ToolbarProvider {
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

    override fun setToolbarTitle(@StringRes titleResId: Int) {
        actionBar?.setTitle(titleResId)
    }

    override fun setToolbarTitle(title: String?) {
        actionBar?.title = title
    }

    override fun setNavigationIcon(@DrawableRes iconResId: Int) {
        actionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(iconResId)
        }
    }

    override fun setCustomView(view: View) {
        setCustomView(view, false)
    }

    override fun setCustomView(view: View, relativeHeight: Boolean) {
        toolbar.run {
            view.tag = CUSTOM_VIEW_TAG
            addView(view)
            setContentInsetsAbsolute(0, 0)
            if (relativeHeight) {
                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            }
        }
    }

    override fun getCustomView(): View? {
        return toolbar.findViewWithTag(CUSTOM_VIEW_TAG)
    }

    companion object {
        private const val CUSTOM_VIEW_TAG = "tag"
    }
}