package com.iron.espresso.presentation.home.study.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iron.espresso.presentation.home.study.model.StudyListItem

class StudyListAdapter :
    RecyclerView.Adapter<StudyListViewHolder>() {
    private val studyList = mutableListOf<StudyListItem>()

    fun setItemList(studyList: List<StudyListItem>) {
        this.studyList.addAll(studyList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudyListViewHolder =
        StudyListViewHolder(parent)

    override fun getItemCount(): Int =
        studyList.size

    override fun onBindViewHolder(holder: StudyListViewHolder, position: Int) {
        holder.bind(studyList[position])
    }
}