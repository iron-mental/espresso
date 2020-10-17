package com.iron.espresso.presentation.home.study.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iron.espresso.presentation.StudyCategoryItem
import com.iron.espresso.presentation.home.study.adapter.viewholder.StudyCategoryViewHolder

class CategoryAdapter : RecyclerView.Adapter<StudyCategoryViewHolder>() {

    private val categoryList = mutableListOf<StudyCategoryItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudyCategoryViewHolder =
        StudyCategoryViewHolder(parent)

    override fun onBindViewHolder(holder: StudyCategoryViewHolder, position: Int) {
        holder.bind(categoryList[position])
    }

    override fun getItemCount(): Int =
        categoryList.size


    fun addAll(itemList: List<StudyCategoryItem>) {
        categoryList.addAll(itemList)
        notifyDataSetChanged()
    }
}