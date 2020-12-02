package com.iron.espresso.presentation.home.mystudy.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.iron.espresso.R
import com.iron.espresso.databinding.ItemNoticeLayoutBinding
import com.iron.espresso.model.response.notice.NoticeResponse

class NoticeViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(
        R.layout.item_notice_layout, parent, false
    )
) {
    private val binding =
        DataBindingUtil.bind<ItemNoticeLayoutBinding>(itemView)

    fun bind(
        item: NoticeResponse,
        nextItem: NoticeResponse?,
        itemClickListener: (noticeItem: NoticeResponse) -> Unit
    ) {
        itemView.setOnClickListener {
            itemClickListener(item)
        }

        binding?.run {
            title.text = item.title
            category.apply {
                when (item.pinned) {
                    true -> {
                        text = context.getString(R.string.pined_true)
                        setBackgroundResource(R.color.theme_fc813e)
                    }
                    false -> {
                        text = context.getString(R.string.pined_false)
                        setBackgroundResource(R.color.colorCobaltBlue)
                    }
                }
            }
            date.text = item.updatedAt
            content.text = item.contents

            divider.apply {
                layoutParams.height =
                    if (item.pinned == true && nextItem?.pinned == false) {
                        resources.getDimension(R.dimen.diff_divide_height).toInt()
                    } else {
                        resources.getDimension(R.dimen.same_divide_height).toInt()
                    }
            }
        }
    }
}