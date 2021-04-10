package com.iron.espresso.presentation.home.alert

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.children
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
                if (!item.confirm){
                    it.alpha = 0.5f
                }
            }
            type.text = item.alertType?.emoji
            title.text = item.studyTitle
            date.text = item.pastDate
            message.text = item.message

            layout.children.forEach {
                it.alpha = if (item.confirm) 0.5f else 1f
            }
        }
    }
}