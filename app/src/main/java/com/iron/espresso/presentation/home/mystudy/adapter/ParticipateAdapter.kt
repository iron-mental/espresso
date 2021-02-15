package com.iron.espresso.presentation.home.mystudy.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iron.espresso.R
import com.iron.espresso.data.model.ParticipateItem
import com.iron.espresso.presentation.home.mystudy.adapter.viewholder.ParticipateViewHolder

class ParticipateAdapter : RecyclerView.Adapter<ParticipateViewHolder>() {

    private lateinit var itemClickListener: (participateItem: ParticipateItem) -> Unit
    private val participateList = mutableListOf<ParticipateItem>()

    fun setItemClickListener(listener: (participateItem: ParticipateItem) -> Unit) {
        itemClickListener = listener
    }

    fun setItemList(participateList: List<ParticipateItem>) {
        this.participateList.clear()
        this.participateList.addAll(participateList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipateViewHolder {
        return ParticipateViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_delegate_member, parent, false), itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ParticipateViewHolder, position: Int) {
        holder.bind(participateList[position])
    }

    override fun getItemCount(): Int =
        participateList.size
}