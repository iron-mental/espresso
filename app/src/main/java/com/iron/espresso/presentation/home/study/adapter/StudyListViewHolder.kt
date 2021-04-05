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
import java.text.SimpleDateFormat
import java.util.*

class StudyListViewHolder(
    parent: ViewGroup,
    private val itemClickListener: (studyItem: StudyItem) -> Unit
) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_study_list, parent, false)
    ) {
    private val binding =
        DataBindingUtil.bind<ItemStudyListBinding>(itemView)

    fun bind(item: StudyItem) {
        itemView.setOnClickListener {
            itemClickListener(item)
        }
        binding?.run {
            title.text = item.title
            location.text = item.sigungu
            if (item.createdAt != -1L) {
                date.text = SimpleDateFormat("yy/MM/dd").format(Date(item.createdAt * 1000L))
            }

            if (item.image.isNullOrEmpty()) {
                image.setImageResource(R.drawable.bg_dash_line)
            } else {
                image.setRadiusImage(item.image)
            }

            if (item.leaderImage.isNullOrEmpty()) {
                profileImage.setImageResource(R.drawable.ic_person)
            } else {
                profileImage.setCircleImage(item.leaderImage)
            }
        }
    }
}