package com.iron.espresso.presentation.home.setting.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.switchmaterial.SwitchMaterial
import com.iron.espresso.R
import com.iron.espresso.presentation.home.setting.model.*

class SettingAdapter(
    private val itemList: List<ItemType>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface ItemClickListener {
        fun onClick(view: View, noticeSwitch: SwitchMaterial? = null)
    }

    private lateinit var itemClickListener: ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    private var viewAdded = 0


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.setting_profile, parent, false)

                HeaderViewHolder(view)
            }
            1 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.setting_category_layout, parent, false)

                ItemHeaderViewHolder(view)
            }
            2 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.setting_item_layout, parent, false)

                ItemViewHolder(view)
            }
            else -> error("Invalid viewType")
        }
    }

    override fun getItemCount(): Int = itemList.count()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> {
                val item = itemList[position] as HeaderItem
                holder.bind(item, itemClickListener)
            }
            is ItemHeaderViewHolder -> {
                val item = itemList[position] as SettingHeaderItem
                holder.bind(item)
            }
            is ItemViewHolder -> {
                val item = itemList[position] as SettingItem
                holder.bind(item, itemClickListener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (itemList[position].type) {
            SettingItemType.HEADER -> 0
            SettingItemType.ITEM_HEADER -> 1
            SettingItemType.ITEM -> 2
        }
    }

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val settingProfileImage: ImageView =
            itemView.findViewById(R.id.setting_profile_image)

        fun bind(item: HeaderItem, itemClickListener: ItemClickListener) {
            itemView.tag = itemViewType
            itemView.setOnClickListener {
                itemClickListener.onClick(it)
            }
            Glide.with(itemView.context)
                .load(R.drawable.ic_launcher_background)
                .circleCrop()
                .into(settingProfileImage)
        }
    }

    class ItemHeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val categoryTitle: TextView = itemView.findViewById(R.id.category_title)

        fun bind(item: SettingHeaderItem) {
            categoryTitle.text = item.categoryTitle
        }
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title: TextView = itemView.findViewById(R.id.setting_item_text)
        private val subItemView: LinearLayout = itemView.findViewById(R.id.setting_item_sub)

        private val button: Button = itemView.findViewById(R.id.setting_item_button)
        private val image: ImageView = itemView.findViewById(R.id.setting_item_image)

        fun bind(item: SettingItem, itemClickListener: ItemClickListener) {
            title.text = item.title
            val noticeSwitch = SwitchMaterial(itemView.context)

            itemView.tag = item.title
            itemView.setOnClickListener {
                if (item.subItemType == SubItemType.SWITCH) {
                    itemClickListener.onClick(it, noticeSwitch)
                } else {
                    itemClickListener.onClick(it)
                }
            }
            subItemView.removeAllViews()

            when (item.subItemType) {
                SubItemType.IMAGE -> {
                    if (itemView.tag == "이메일") {
                        button.text = "인증"
                        subItemView.addView(button)
                    } else {
                        image.setImageResource(R.drawable.ic_github)
                        subItemView.addView(image)
                    }
                }
                SubItemType.SWITCH -> {
                    subItemView.addView(noticeSwitch)
                    noticeSwitch.isClickable = false
                }
                SubItemType.INFO -> {
                    if (itemView.tag == itemView.context.getString(R.string.app_version)) {
                        val text = TextView(itemView.context)
                        text.text = itemView.context.getString(R.string.version_text)
                        subItemView.addView(text)
                    } else {
                        val img = ImageView(itemView.context)
                        img.setImageResource(R.drawable.ic_next)
                        subItemView.addView(img)
                    }
                }
                SubItemType.NONE -> {
                }
            }
        }
    }
}