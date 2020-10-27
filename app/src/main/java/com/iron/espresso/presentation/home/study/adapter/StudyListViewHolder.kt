package com.iron.espresso.presentation.home.study.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
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

    fun bind(item: StudyListItem) {
        binding?.run {
            title.text = item.title
            caption.text = item.caption
            location.text = item.location
            date.text = item.date
        }
    }
}