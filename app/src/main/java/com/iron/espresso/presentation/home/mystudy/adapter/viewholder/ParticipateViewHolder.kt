package com.iron.espresso.presentation.home.mystudy.adapter.viewholder

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.iron.espresso.data.model.ParticipateItem
import com.iron.espresso.databinding.ItemDelegateMemberBinding
import com.iron.espresso.ext.setCircleImage

class ParticipateViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding: ItemDelegateMemberBinding? = DataBindingUtil.bind(itemView)

    fun bind(item: ParticipateItem) {
        binding?.run {
            if (!item.image.isNullOrEmpty()) {
                memberImage.setCircleImage(item.image)
            }
            memberNickname.text = item.nickname
        }
    }

}