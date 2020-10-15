package com.iron.espresso.presentation.home.study

import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.iron.espresso.R

class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val title: Button = itemView.findViewById(R.id.hot_keyword_button)

    fun bind(item: HotKeywordItem){
        title.text = item.title
    }
}
