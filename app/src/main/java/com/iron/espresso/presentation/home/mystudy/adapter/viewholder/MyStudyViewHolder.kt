package com.iron.espresso.presentation.home.mystudy.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.iron.espresso.BR
import com.iron.espresso.R
import com.iron.espresso.data.model.MyStudyItem
import com.iron.espresso.databinding.ItemMystudyBinding

class MyStudyViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(
        R.layout.item_mystudy, parent, false
    )
) {
    private val binding =
        DataBindingUtil.bind<ItemMystudyBinding>(itemView)

    fun bind(item: MyStudyItem) {
        binding?.run {
            setVariable(BR.myStudyItem, item)
            executePendingBindings()
        }
    }
}