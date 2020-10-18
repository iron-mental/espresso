package com.iron.espresso.presentation

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleObserver

class BindingHelper<T : ViewDataBinding> :
    LifecycleObserver {

    var binding: T? = null

    companion object {
        private var instance: BindingHelper<*>? = null

        @Suppress("UNCHECKED_CAST")
        fun <T : ViewDataBinding> getBinding(
            activity: AppCompatActivity,
            @LayoutRes layoutId: Int
        ): T {
            val helper = instance as? BindingHelper<T>
                ?: BindingHelper<T>().also {
                    instance = it
                }

            return helper.binding ?: DataBindingUtil.setContentView<T>(activity, layoutId)
                .also {
                    helper.binding = it
                    it.lifecycleOwner = activity
                }
        }
    }
}