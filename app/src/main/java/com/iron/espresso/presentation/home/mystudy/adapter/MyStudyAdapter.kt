package com.iron.espresso.presentation.home.mystudy.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iron.espresso.data.model.MyStudyViewType
import com.iron.espresso.model.response.study.MyStudyResponse
import com.iron.espresso.presentation.home.mystudy.adapter.viewholder.MyStudyViewHolder

class MyStudyAdapter : RecyclerView.Adapter<MyStudyViewHolder>() {

    interface ItemClickListener {
        fun onClick(item: MyStudyResponse)
    }

    private lateinit var itemClickListener: ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    private val myStudyList = mutableListOf<MyStudyResponse>()
    private var myStudyViewType = MyStudyViewType.LIST

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyStudyViewHolder =
        MyStudyViewHolder(parent)

    override fun getItemCount(): Int =
        myStudyList.size

    override fun onBindViewHolder(holder: MyStudyViewHolder, position: Int) {
        holder.bind(myStudyList[position], itemClickListener, myStudyViewType)
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

    fun setMyStudyView(type: MyStudyViewType) {
        this.myStudyViewType = type
    }
}