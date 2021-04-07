package com.iron.espresso.presentation.home.mystudy.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.iron.espresso.R
import com.iron.espresso.data.model.MyStudyItem
import com.iron.espresso.databinding.ItemMystudyBinding
import com.iron.espresso.ext.setRadiusImage
import com.iron.espresso.presentation.home.mystudy.adapter.MyStudyAdapter

class MyStudyViewHolder(
    parent: ViewGroup,
    private val itemClickListener: MyStudyAdapter.ItemClickListener
) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_mystudy, parent, false
        )
    ) {
    private val binding =
        DataBindingUtil.bind<ItemMystudyBinding>(itemView)

    fun bind(item: MyStudyItem) {
        binding?.run {
            location.text = item.sigungu
            name.text = item.title

            if (item.image.isEmpty()) {
                image.setImageResource(R.drawable.bg_dash_line)
            } else {
                image.setRadiusImage(item.image)
            }

            itemView.setOnClickListener {
                itemClickListener.onClick(item)
            }
        }
    }
}