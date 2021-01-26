package com.iron.espresso.presentation.home.study.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.iron.espresso.R
import com.iron.espresso.data.model.StudyItem
import com.iron.espresso.databinding.ItemStudyListBinding
import com.iron.espresso.ext.setCircleImage
import com.iron.espresso.ext.setRadiusImage

class StudyListViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(
        R.layout.item_study_list, parent, false
    )
) {
    private val binding =
        DataBindingUtil.bind<ItemStudyListBinding>(itemView)

    fun bind(item: StudyItem, itemClickListener: (studyItem: StudyItem) -> Unit) {
        itemView.setOnClickListener {
            itemClickListener(item)
        }
        binding?.run {
            title.text = item.title
            location.text = item.sigungu
            date.text = item.createdAt

            if (item.image.isNullOrEmpty()) {
                image.setImageResource(R.drawable.dummy_image)
            } else {
                image.setRadiusImage(item.image)
            }

            if (item.leaderImage.isNullOrEmpty()) {
                profileImage.setImageResource(R.drawable.dummy_image)
            } else {
                profileImage.setCircleImage(item.leaderImage)
            }
        }
    }
}