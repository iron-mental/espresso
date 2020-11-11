package com.iron.espresso.presentation.home.study.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.iron.espresso.R
import com.iron.espresso.data.model.PlaceItem

class PlaceViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_place_list, parent, false)
) {
    private val binding =
        DataBindingUtil.bind<com.iron.espresso.databinding.ItemPlaceListBinding>(itemView)

    fun bind(item: PlaceItem) {
        binding?.run {
            title.text = item.title
            category.text = item.category
            address.text = item.address
        }

    }
}