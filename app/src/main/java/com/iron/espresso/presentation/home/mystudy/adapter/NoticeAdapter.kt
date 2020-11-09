package com.iron.espresso.presentation.home.mystudy.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iron.espresso.data.model.NoticeListItem
import com.iron.espresso.presentation.home.mystudy.adapter.viewholder.NoticeViewHolder

class NoticeAdapter :
    RecyclerView.Adapter<NoticeViewHolder>() {

    lateinit var itemClickListener: (title: String) -> Unit

    private val noticeList = mutableListOf<NoticeListItem>()

    fun setItemList(noticeList: List<NoticeListItem>) {
        this.noticeList.clear()
        this.noticeList.addAll(noticeList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeViewHolder =
        NoticeViewHolder(parent)

    override fun getItemCount(): Int =
        noticeList.size

    override fun onBindViewHolder(holder: NoticeViewHolder, position: Int) {
        holder.bind(noticeList[position], noticeList.getOrNull(position + 1), itemClickListener)
    }
}