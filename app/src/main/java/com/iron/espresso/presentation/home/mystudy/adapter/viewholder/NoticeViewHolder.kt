package com.iron.espresso.presentation.home.mystudy.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.iron.espresso.R
import com.iron.espresso.data.model.NoticeListItem
import com.iron.espresso.databinding.ItemNoticeLayoutBinding

class NoticeViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(
        R.layout.item_notice_layout, parent, false
    )
) {
    private val binding =
        DataBindingUtil.bind<ItemNoticeLayoutBinding>(itemView)

    fun bind(item: NoticeListItem) {
        binding?.run {
            title.text = item.title
            category.text = item.category
            version.text = item.version
            content.text = item.content
        }
    }
}