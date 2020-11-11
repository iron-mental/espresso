package com.iron.espresso.presentation.home.study.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iron.espresso.data.model.PlaceItem
import com.iron.espresso.presentation.home.study.adapter.viewholder.PlaceViewHolder

class PlaceAdapter : RecyclerView.Adapter<PlaceViewHolder>() {

    private val placeList = mutableListOf<PlaceItem>()

    fun setItemList(placeList: List<PlaceItem>) {
        this.placeList.clear()
        this.placeList.addAll(placeList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder =
        PlaceViewHolder(parent)

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.bind(placeList[position])
    }

    override fun getItemCount(): Int =
        placeList.size

}