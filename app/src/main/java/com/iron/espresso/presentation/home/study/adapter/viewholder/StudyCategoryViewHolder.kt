package com.iron.espresso.presentation.home.study.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.iron.espresso.R
import com.iron.espresso.databinding.ItemStudyCategoryBinding
import com.iron.espresso.di.ApiModule
import com.iron.espresso.ext.setImage

class StudyCategoryViewHolder(
    parent: ViewGroup,
    private val clickListener: (String) -> Unit,
    private val binding: ItemStudyCategoryBinding =
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_study_category,
            parent,
            false
        )
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: String) {

        binding.ivImage.apply {
            setOnClickListener { clickListener(item) }
            if (item != "etc") {
                setImage("${ApiModule.API_URL}/images/category/${item}.png")
            }
        }
        binding.title.text = item
    }
}