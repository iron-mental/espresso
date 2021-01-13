package com.wswon.picker.common

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewAdapter<ITEM : Any, B : ViewDataBinding, VH : BaseRecyclerViewHolder<B>>(
    @LayoutRes private val layoutRes: Int,
    private val variableId: Int? = null,
    private val widthRatio: Float = 1f,
    private val heightRatio: Float = 1f
) : RecyclerView.Adapter<VH>() {
    protected val items = mutableListOf<ITEM>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        object : BaseRecyclerViewHolder<B>(
            layoutRes = layoutRes,
            parent = parent,
            variableId = variableId,
            widthRatio = widthRatio,
            heightRatio = heightRatio
        ) {} as VH

    override fun getItemCount(): Int =
        items.size

    override fun onBindViewHolder(holder: VH, position: Int) =
        holder.bind(items[position])

    fun add(item: ITEM?) {
        item?.let {
            items.add(it)
            notifyItemChanged(items.lastIndex)
        }
    }

    fun addAll(items: List<ITEM>?) {
        items?.let {
            this.items.addAll(it)
            notifyDataSetChanged()
        }
    }

    fun getAllItem(): List<ITEM> =
        items

    fun removeItem(item: ITEM?) {
        item?.let {
            val removeIndex = items.indexOf(it)
            items.remove(it)
            notifyItemRemoved(removeIndex)
        }
    }

    fun replaceAll(items: List<ITEM>?) {
        items?.let {
            this.items.run {
                clear()
                addAll(it)
            }
            notifyDataSetChanged()
        }
    }
}