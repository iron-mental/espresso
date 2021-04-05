package com.iron.espresso.presentation.home.study.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iron.espresso.presentation.home.study.adapter.viewholder.StudyCategoryViewHolder

class CategoryAdapter(private val clickListener: (String) -> Unit) :
    RecyclerView.Adapter<StudyCategoryViewHolder>() {

    private val categoryList = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudyCategoryViewHolder =
        StudyCategoryViewHolder(parent, clickListener)

    override fun onBindViewHolder(holder: StudyCategoryViewHolder, position: Int) {
        holder.bind(categoryList[position])
    }

    override fun getItemCount(): Int =
        categoryList.size


    fun addAll(itemList: List<String>) {
        categoryList.addAll(itemList)
        notifyDataSetChanged()
    }
}