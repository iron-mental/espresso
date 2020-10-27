package com.iron.espresso.presentation.home.study.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iron.espresso.presentation.home.study.model.StudyListItem

class StudyListAdapter(private val studyList: List<StudyListItem>) :
    RecyclerView.Adapter<StudyListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudyListViewHolder =
        StudyListViewHolder(parent)

    override fun getItemCount(): Int =
        studyList.size

    override fun onBindViewHolder(holder: StudyListViewHolder, position: Int) {
        holder.bind(studyList[position])
    }
}