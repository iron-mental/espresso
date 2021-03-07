package com.iron.espresso.presentation.home.alert

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iron.espresso.databinding.ItemAlertBinding

class AlertListViewHolder(
    parent: ViewGroup,
    private val itemClick: (AlertItem) -> Unit,
    private val binding: ItemAlertBinding =
        ItemAlertBinding.inflate(LayoutInflater.from(parent.context), parent, false)
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: AlertItem) {
        with(binding) {
            root.setOnClickListener {
                itemClick(item)
            }
            type.text = item.alertType?.emoji
            title.text = item.studyTitle
            date.text = item.pastDate
            message.text = item.message
        }
    }
}