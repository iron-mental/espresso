package com.iron.espresso.presentation.home.study.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.iron.espresso.AuthHolder
import com.iron.espresso.R
import com.iron.espresso.data.model.CreateStudyItem
import com.iron.espresso.data.model.StudyItem
import com.iron.espresso.databinding.ItemStudyListBinding

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

            Glide.with(itemView)
                .load(
                    if (item.image.isNullOrEmpty()) {
                        R.drawable.dummy_image
                    } else {
                        GlideUrl(
                            item.image,
                            LazyHeaders.Builder()
                                .addHeader("Authorization", AuthHolder.bearerToken)
                                .build()
                        )
                    }
                )
                .transform(CenterCrop(), RoundedCorners(30))
                .error(R.drawable.dummy_image)
                .into(image)

            Glide.with(itemView)
                .load(
                    if (item.leaderImage.isNullOrEmpty()) {
                        R.drawable.dummy_image
                    } else {
                        GlideUrl(
                            item.leaderImage,
                            LazyHeaders.Builder()
                                .addHeader("Authorization", AuthHolder.bearerToken)
                                .build()
                        )
                    }
                )
                .circleCrop()
                .error(R.drawable.dummy_image)
                .into(profileImage)
        }
    }
}