package com.iron.espresso.presentation.home.mystudy.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iron.espresso.model.response.notice.NoticeResponse
import com.iron.espresso.presentation.home.mystudy.adapter.viewholder.NoticeViewHolder

class NoticeAdapter :
    RecyclerView.Adapter<NoticeViewHolder>() {

    private lateinit var itemClickListener: (noticeItem: NoticeResponse) -> Unit

    fun setItemClickListener(listener: (noticeItem: NoticeResponse) -> Unit) {
        itemClickListener = listener
    }

    private val noticeList = mutableListOf<NoticeResponse>()

    fun setItemList(noticeList: List<NoticeResponse>) {
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