package com.wswon.picker.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView


/**
 * @param layoutRes ViewHolder의 layout resource
 * @param parent parent
 * @param variableId binding Item Id
 * */
abstract class BaseRecyclerViewHolder<B : ViewDataBinding>(
    @LayoutRes layoutRes: Int,
    parent: ViewGroup,
    private val widthRatio: Float = 1f,
    private val heightRatio: Float = 1f,
    private val variableId: Int?
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(layoutRes, parent, false).apply {
            if (widthRatio != 1f || heightRatio != 1f) {
                layoutParams =
                    RecyclerView.LayoutParams(
                        (parent.measuredWidth * widthRatio).toInt(),
                        (parent.measuredHeight * heightRatio).toInt()
                    )
            }
        }
) {


    val binding: B? = DataBindingUtil.bind(itemView)

    fun bind(item: Any?) {
        binding?.run {
            variableId?.let {
                setVariable(it, item)
            }
            executePendingBindings()
        }
    }
}