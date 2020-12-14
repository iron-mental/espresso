package com.iron.espresso.presentation.home.study.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iron.espresso.model.response.study.StudyListResponse
import com.iron.espresso.model.response.study.StudyResponse

class StudyListAdapter :
    RecyclerView.Adapter<StudyListViewHolder>() {

    lateinit var itemClickListener: (title: String) -> Unit
    private val studyList = mutableListOf<StudyResponse>()

    fun setItemList(studyList: StudyListResponse) {
        this.studyList.clear()
        this.studyList.addAll(studyList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudyListViewHolder =
        StudyListViewHolder(parent)

    override fun getItemCount(): Int =
        studyList.size

    override fun onBindViewHolder(holder: StudyListViewHolder, position: Int) {
        holder.bind(studyList[position], itemClickListener)
    }
}