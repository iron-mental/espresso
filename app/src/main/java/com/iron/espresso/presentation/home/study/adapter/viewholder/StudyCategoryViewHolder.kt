package com.iron.espresso.presentation.home.study.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.iron.espresso.R
import com.iron.espresso.presentation.StudyCategoryItem

class StudyCategoryViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(
                R.layout.item_study_category, parent, false
        )
) {
    private val binding =
            DataBindingUtil.bind<com.iron.espresso.databinding.ItemStudyCategoryBinding>(itemView)

    fun bind(item: StudyCategoryItem) {
        binding?.run {
            setVariable(BR.studyCategoryItem, item)
            executePendingBindings()
        }
    }
}