package com.iron.espresso.presentation.home.mystudy.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.iron.espresso.R
import com.iron.espresso.data.model.NoticeItem
import com.iron.espresso.databinding.ItemNoticeLayoutBinding

class NoticeViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(
        R.layout.item_notice_layout, parent, false
    )
) {
    private val binding =
        DataBindingUtil.bind<ItemNoticeLayoutBinding>(itemView)

    fun bind(
        item: NoticeItem,
        nextItem: NoticeItem?,
        itemClickListener: (noticeItem: NoticeItem) -> Unit
    ) {
        itemView.setOnClickListener {
            itemClickListener(item)
        }

        binding?.run {
            title.text = item.title
            category.apply {
                if (item.pinned) {
                    text = context.getString(R.string.pined_true)
                    backgroundTintList = resources.getColorStateList(R.color.theme_fc813e, null)
                } else {
                    text = context.getString(R.string.pined_false)
                    backgroundTintList = resources.getColorStateList(R.color.colorCobaltBlue, null)

                }
            }
            date.text = item.updatedAt
            content.text = item.contents

            divider.apply {
                layoutParams.height =
                    if (item.pinned && nextItem?.pinned == false) {
                        resources.getDimension(R.dimen.diff_divide_height).toInt()
                    } else {
                        resources.getDimension(R.dimen.same_divide_height).toInt()
                    }
            }
        }
    }
}