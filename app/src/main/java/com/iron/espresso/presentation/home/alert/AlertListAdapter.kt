package com.iron.espresso.presentation.home.alert

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

class AlertListAdapter(private val itemClick: (AlertItem) -> Unit) :
    ListAdapter<AlertItem, AlertListViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertListViewHolder =
        AlertListViewHolder(parent, itemClick)

    override fun onBindViewHolder(holder: AlertListViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AlertItem>() {
            override fun areItemsTheSame(oldItem: AlertItem, newItem: AlertItem): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: AlertItem, newItem: AlertItem): Boolean =
                oldItem == newItem
        }
    }
}