package com.iron.espresso.presentation.home.mystudy.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iron.espresso.data.model.NoticeListItem
import com.iron.espresso.presentation.home.mystudy.adapter.viewholder.NoticeViewHolder

class NoticeAdapter(private val noticeList: List<NoticeListItem>) :
    RecyclerView.Adapter<NoticeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeViewHolder =
        NoticeViewHolder(parent)

    override fun getItemCount(): Int =
        noticeList.size

    override fun onBindViewHolder(holder: NoticeViewHolder, position: Int) {
        holder.bind(noticeList[position])
    }
}