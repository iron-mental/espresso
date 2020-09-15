package com.iron.espresso.presentation.home.setting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iron.espresso.R
import kotlinx.android.synthetic.main.setting_item_layout.view.*

class SettingItemAdapter(private val item: List<ItemList>) :
    RecyclerView.Adapter<SettingItemAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.setting_item_layout,parent,false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return item.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemList = item[position]
        holder.itemTitle.text = itemList.title

    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val itemTitle: TextView = view.setting_item_text
    }
}