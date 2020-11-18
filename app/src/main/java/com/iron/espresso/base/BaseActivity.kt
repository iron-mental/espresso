package com.iron.espresso.base

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.appbar.AppBarLayout

abstract class BaseActivity<B : ViewDataBinding>(@LayoutRes private val layoutId: Int) :
    AppCompatActivity(),
    ToolbarProvider {

    protected lateinit var binding: B

    private var toolbarHelper: ToolbarHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this
        initToolbarHelper()
    }

    private fun initToolbarHelper() {
        val appBarLayout =
            (binding.root as ViewGroup).children.find { it is AppBarLayout } as? AppBarLayout

        if (appBarLayout != null) {
            toolbarHelper = ToolbarHelper(this, appBarLayout)
        }
    }

    override fun setToolbarTitle(@StringRes titleResId: Int) {
        toolbarHelper?.setToolbarTitle(titleResId)
    }

    override fun setToolbarTitle(title: String) {
        toolbarHelper?.setToolbarTitle(title)
    }

    override fun setNavigationIcon(@DrawableRes iconResId: Int) {
        toolbarHelper?.setNavigationIcon(iconResId)
    }

    override fun setCustomView(view: View) {
        toolbarHelper?.setCustomView(view)
    }

    override fun setCustomView(view: View, relativeHeight: Boolean) {
        toolbarHelper?.setCustomView(view, relativeHeight)
    }
}