package com.iron.espresso.presentation.home.apply

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iron.espresso.databinding.ItemApplyStudyBinding
import com.iron.espresso.ext.setRadiusImage

class ApplyViewHolder(
    parent: ViewGroup,
    private val itemClick: (ApplyStudyItem) -> Unit,
    private val binding: ItemApplyStudyBinding =
        ItemApplyStudyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ApplyStudyItem) {
        with(binding) {
            image.setRadiusImage(item.image)
            title.text = item.title
            content.text = item.message
            root.setOnClickListener {
                itemClick(item)
            }
        }
    }
}