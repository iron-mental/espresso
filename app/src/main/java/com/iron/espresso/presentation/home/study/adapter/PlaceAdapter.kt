package com.iron.espresso.presentation.home.study.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iron.espresso.model.response.Place
import com.iron.espresso.presentation.home.study.adapter.viewholder.PlaceViewHolder

class PlaceAdapter : RecyclerView.Adapter<PlaceViewHolder>() {

    private lateinit var itemClickListener: (title: String) -> Unit
    private val placeList = mutableListOf<Place>()

    fun setItemClickListener(listener: (title: String) -> Unit) {
        this.itemClickListener = listener
    }

    fun setItemList(placeList: List<Place>) {
        this.placeList.clear()
        this.placeList.addAll(placeList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder =
        PlaceViewHolder(parent, itemClickListener)

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.bind(placeList[position])
    }

    override fun getItemCount(): Int =
        placeList.size

}