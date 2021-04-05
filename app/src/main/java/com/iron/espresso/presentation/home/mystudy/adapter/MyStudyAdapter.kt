package com.iron.espresso.presentation.home.mystudy.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iron.espresso.data.model.MyStudyItem
import com.iron.espresso.presentation.home.mystudy.adapter.viewholder.MyStudyViewHolder

class MyStudyAdapter : RecyclerView.Adapter<MyStudyViewHolder>() {

    interface ItemClickListener {
        fun onClick(item: MyStudyItem)
    }

    private lateinit var itemClickListener: ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    private val myStudyList = mutableListOf<MyStudyItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyStudyViewHolder =
        MyStudyViewHolder(parent, itemClickListener)

    override fun getItemCount(): Int =
        myStudyList.size

    override fun onBindViewHolder(holder: MyStudyViewHolder, position: Int) {
        holder.bind(myStudyList[position])
    }

    fun replaceAll(list: List<MyStudyItem>?) {
        list?.let {
            myStudyList.apply {
                clear()
                addAll(list)
                notifyDataSetChanged()
            }
        }
    }
}