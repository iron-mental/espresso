package com.iron.espresso.presentation.home.apply

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

class MyApplyStudyAdapter(private val itemClick: (MyApplyStudyItem) -> Unit) :
    ListAdapter<MyApplyStudyItem, MyApplyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyApplyViewHolder {
        return MyApplyViewHolder(parent, itemClick)
    }

    override fun onBindViewHolder(holder: MyApplyViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MyApplyStudyItem>() {
            override fun areItemsTheSame(
                oldItem: MyApplyStudyItem,
                newItem: MyApplyStudyItem
            ): Boolean =
                oldItem.message == newItem.message

            override fun areContentsTheSame(
                oldItem: MyApplyStudyItem,
                newItem: MyApplyStudyItem
            ): Boolean =
                oldItem == newItem
        }
    }
}
