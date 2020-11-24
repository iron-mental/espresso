package com.iron.espresso.presentation.home.study.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.iron.espresso.R
import com.iron.espresso.databinding.ItemStudyListBinding
import com.iron.espresso.presentation.home.study.model.StudyListItem

class StudyListViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(
        R.layout.item_study_list, parent, false
    )
) {
    private val binding =
        DataBindingUtil.bind<ItemStudyListBinding>(itemView)

    fun bind(item: StudyListItem, itemClickListener: (title: String) -> Unit) {
        itemView.setOnClickListener {
            itemClickListener(item.title)
        }
        binding?.run {
            title.text = item.title
            caption.text = item.caption
            location.text = item.location
            date.text = item.date

            Glide.with(itemView)
                .load("https://appsamurai.com/wp-content/uploads/2017/07/android-and-ios-development.jpg")
                .transform(CenterCrop(), RoundedCorners(30))
                .into(image)

            Glide.with(itemView)
                .load("https://www.leaders.kr/news/photo/201709/54250_45831_2657.jpg")
                .circleCrop()
                .into(profileImage)
        }
    }
}