package com.iron.espresso.presentation.home.study.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iron.espresso.data.model.StudyItem

class StudyListAdapter :
    RecyclerView.Adapter<StudyListViewHolder>() {

    private lateinit var itemClickListener: (studyItem: StudyItem) -> Unit
    private val studyList = mutableListOf<StudyItem>()

    fun setItemClickListener(listener: (studyItem: StudyItem) -> Unit) {
        itemClickListener = listener
    }

    fun setItemList(studyList: List<StudyItem>) {
        this.studyList.clear()
        this.studyList.addAll(studyList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudyListViewHolder =
        StudyListViewHolder(parent, itemClickListener)

    override fun getItemCount(): Int =
        studyList.size

    override fun onBindViewHolder(holder: StudyListViewHolder, position: Int) {
        holder.bind(studyList[position])
    }
}