package com.iron.espresso.presentation.home.apply

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

class ApplyStudyAdapter(private val itemClick: (ApplyStudyItem) -> Unit) :
    ListAdapter<ApplyStudyItem, ApplyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplyViewHolder {
        return ApplyViewHolder(parent, itemClick)
    }

    override fun onBindViewHolder(holder: ApplyViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ApplyStudyItem>() {
            override fun areItemsTheSame(
                oldItem: ApplyStudyItem,
                newItem: ApplyStudyItem
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: ApplyStudyItem,
                newItem: ApplyStudyItem
            ): Boolean =
                oldItem == newItem
        }
    }
}
