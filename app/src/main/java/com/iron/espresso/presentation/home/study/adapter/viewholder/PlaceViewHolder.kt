package com.iron.espresso.presentation.home.study.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.iron.espresso.R
import com.iron.espresso.model.response.Place

class PlaceViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_place_list, parent, false)
) {
    private val binding =
        DataBindingUtil.bind<com.iron.espresso.databinding.ItemPlaceListBinding>(itemView)

    fun bind(item: Place, itemClickListener: (item: Place) -> Unit) {
        itemView.setOnClickListener {
            itemClickListener(item)
        }
        binding?.run {
            title.text = item.place_name
            category.text = item.categoryGroupName
            address.text = item.addressName
        }

    }
}