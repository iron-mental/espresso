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
import com.iron.espresso.model.response.study.StudyResponse

class StudyListViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(
        R.layout.item_study_list, parent, false
    )
) {
    private val binding =
        DataBindingUtil.bind<ItemStudyListBinding>(itemView)

    fun bind(item: StudyResponse, itemClickListener: (title: String) -> Unit) {
        itemView.setOnClickListener {
            item.title?.let { title -> itemClickListener(title) }
        }
        binding?.run {
            title.text = item.title
            caption.text = item.introduce
            location.text = item.sigungu
            date.text = item.createdAt

            Glide.with(itemView)
                .load(item.image)
                .transform(CenterCrop(), RoundedCorners(30))
                .error(R.drawable.dummy_image)
                .into(image)

            Glide.with(itemView)
                .load(item.leaderImage)
                .circleCrop()
                .error(R.drawable.dummy_image)
                .into(profileImage)
        }
    }
}