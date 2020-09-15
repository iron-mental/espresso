package com.iron.espresso.presentation.home.setting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iron.espresso.R
import kotlinx.android.synthetic.main.setting_category_layout.view.*

class SettingAdapter(private val item: List<CategoryItem>) :
    RecyclerView.Adapter<SettingAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.setting_category_layout,parent,false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return item.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemList = item[position]
        holder.categoryTitle.text = itemList.categoryTitle

    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val categoryTitle: TextView = view.category_title
        val categoryRecyclerView: RecyclerView = view.category_recyclerview
    }
}