package com.iron.espresso.presentation.home.setting

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.iron.espresso.R
import kotlinx.android.synthetic.main.setting_item_layout.view.*

class SettingItemAdapter(
    private val context: Context,
    private val item: List<ItemList>
) :
    RecyclerView.Adapter<SettingItemAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.setting_item_layout, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return item.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemList = item[position]
        holder.itemTitle.text = itemList.title
        if (holder.itemTitle.text == "푸쉬 알림 사용") {
            val noticeSwitch = Switch(context)
            holder.subItem.addView(noticeSwitch)
        } else if (holder.itemTitle.text == "이메일" || holder.itemTitle.text == "SNS") {
            val img = ImageView(context)
            Glide.with(context)
                .load(R.drawable.ic_launcher_background)
                .apply(RequestOptions().override(150, 150))
                .apply(RequestOptions.centerCropTransform())
                .into(img)
            holder.subItem.addView(img)
        } else {
            val textView = TextView(context).apply {
                text = "〉"
            }
            holder.subItem.addView(textView)
        }
    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val itemTitle: TextView = view.setting_item_text
        val subItem: LinearLayout = view.setting_sub_item

    }
}