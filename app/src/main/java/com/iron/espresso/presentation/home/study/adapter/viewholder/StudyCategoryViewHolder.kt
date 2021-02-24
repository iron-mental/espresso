package com.iron.espresso.presentation.home.study.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.iron.espresso.R
import com.iron.espresso.ext.setImage

class StudyCategoryViewHolder(parent: ViewGroup) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_study_category, parent, false
        )
    ) {
    private val binding =
        DataBindingUtil.bind<com.iron.espresso.databinding.ItemStudyCategoryBinding>(itemView)

    fun bind(item: String, listener: StudyCategoryAdapterListener) {

        binding?.ivImage?.apply {
            setOnClickListener { listener.getData(item, this) }
            setImage("https://www.terminal-study.tk/images/category/${item}.png")
        }
        binding?.title?.text = item
    }
}

interface StudyCategoryAdapterListener {
    fun getData(item: String, imageView: ImageView)
}