package com.iron.espresso.presentation.home.mystudy.adapter.viewholder

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.iron.espresso.data.model.ParticipateItem
import com.iron.espresso.databinding.ItemMemberBinding
import com.iron.espresso.ext.setCircleImage

class ParticipateViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding: ItemMemberBinding? = DataBindingUtil.bind(itemView)

    fun bind(item: ParticipateItem) {
        if (!item.image.isNullOrEmpty()) {
            binding?.memberImage?.setCircleImage(item.image)
        }
        binding?.memberNickname?.text = item.nickname
    }

}