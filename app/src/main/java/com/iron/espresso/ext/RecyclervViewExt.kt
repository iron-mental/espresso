package com.iron.espresso.ext

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iron.espresso.data.model.MyStudyItem
import com.iron.espresso.presentation.home.mystudy.adapter.MyStudyAdapter

@BindingAdapter("bind:replaceAll")
fun RecyclerView.replaceAll(list: List<MyStudyItem>?) {
    @Suppress("UNCHECKED_CAST")
    list?.let {
        (adapter as? MyStudyAdapter)?.replaceAll(it)
    }
}