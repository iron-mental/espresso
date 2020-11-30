package com.iron.espresso.presentation.home.mystudy.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iron.espresso.model.response.study.MyStudyResponse
import com.iron.espresso.presentation.home.mystudy.adapter.viewholder.MyStudyViewHolder

class MyStudyAdapter : RecyclerView.Adapter<MyStudyViewHolder>() {

    interface ItemClickListener {
        fun onClick(view: View)
    }

    private lateinit var itemClickListener: ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    private val myStudyList = mutableListOf<MyStudyResponse>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyStudyViewHolder =
        MyStudyViewHolder(parent)

    override fun getItemCount(): Int =
        myStudyList.size

    override fun onBindViewHolder(holder: MyStudyViewHolder, position: Int) {
        holder.bind(myStudyList[position], itemClickListener)
    }

    fun replaceAll(list: List<MyStudyResponse>?) {
        list?.let {
            myStudyList.apply {
                clear()
                addAll(list)
                notifyDataSetChanged()
            }
        }
    }
}