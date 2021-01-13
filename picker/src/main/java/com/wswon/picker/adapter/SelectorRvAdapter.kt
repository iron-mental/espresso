package com.wswon.picker.adapter

import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.wswon.picker.BR
import com.wswon.picker.ImagePickerSelectorItem
import com.wswon.picker.R
import com.wswon.picker.common.BaseRecyclerViewAdapter
import com.wswon.picker.common.BaseRecyclerViewHolder
import com.wswon.picker.databinding.ItemImagePickerSelectorBinding
import com.wswon.picker.ext.setUriImg

class SelectorRvAdapter(
    private val onItemClick: (position: Int) -> Unit,
    private val onItemSelected: (position: Int, isSelected: Boolean) -> Unit
) : BaseRecyclerViewAdapter<ImagePickerSelectorItem, ItemImagePickerSelectorBinding, SelectorRvAdapter.SelectorViewHolder>(
    R.layout.item_image_picker_selector
) {

    private val selectedPositionList = mutableListOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SelectorViewHolder(parent)

    override fun onBindViewHolder(
        holder: SelectorViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        when {
            payloads.contains(NUM_REFRESH) -> holder.changeSelectNum()
            payloads.contains(CHECKER_VISIBLE_REFRESH) -> holder.executeVisible(items[position])
            payloads.contains(STATE_REFRESH) -> holder.stateVisible(items[position])
            else -> holder.bind(items[position])
        }
    }

    /* image load failed item 처리 - 선택 못하도록 */
    fun setItemLoadFailed(position: Int) {
        items[position].isLoaded = false
        notifyItemChanged(position, CHECKER_VISIBLE_REFRESH)
    }

    /* 아이템 선택 순서를 나타내는 숫자 변경 */
    fun replaceSelectedPositionList(positionList: List<Int>) {

        val isItemAdded = positionList.size >= selectedPositionList.size

        val removedIndex = selectedPositionList.indexOfFirst { !positionList.contains(it) }

        val removedPosition = selectedPositionList.getOrElse(removedIndex) { -1 }

        selectedPositionList.run {
            clear()
            addAll(positionList)
        }

        if (isItemAdded) { // add
            if (positionList.isNotEmpty()) {
                if (removedPosition != -1) {
                    items[removedPosition].isSelected = false

//                    cbSelect.isChecked = false
                    notifyItemChanged(removedPosition, STATE_REFRESH)
                }
                notifyItemChanged(positionList.last(), STATE_REFRESH)
            }
        } else {  // remove
            if (removedIndex != -1) {
                if (removedPosition != -1) {
                    notifyItemChanged(removedPosition, STATE_REFRESH)
                }

                for (i in removedIndex..positionList.lastIndex) {
                    notifyItemChanged(positionList[i], NUM_REFRESH)
                }
            }
        }
    }

    inner class SelectorViewHolder(parent: ViewGroup) :
        BaseRecyclerViewHolder<ItemImagePickerSelectorBinding>(
            layoutRes = R.layout.item_image_picker_selector,
            parent = parent,
            variableId = BR.item
        ) {

        fun changeSelectNum() {
            binding?.run {
                tvSelectedNum.run {
                    val selectNum = selectedPositionList.indexOf(adapterPosition)
                    text = if (selectNum != -1) {
                        "${selectNum + 1}"
                    } else {
                        ""
                    }
                }
            }
        }

        fun stateVisible(item: ImagePickerSelectorItem?) {
            binding?.run {
                item?.let { item ->
                    if (item.isSelected) {
                        vSelectedRect.background =
                            ContextCompat.getDrawable(itemView.context, R.drawable.rect_selected)
                    } else {
                        vSelectedRect.background = null
                    }

                    cbSelect.run {
                        setOnCheckedChangeListener(null)
                        isChecked = item.isSelected
                        setOnCheckedChangeListener { _, isChecked ->
                            item.isSelected = isChecked
                            onItemSelected(adapterPosition, isChecked)
                        }
                    }

                    changeSelectNum()
                }
            }
        }

        fun executeVisible(item: ImagePickerSelectorItem?) {
            binding?.run {
                item?.let {
                    cbSelect.isVisible = it.isLoaded
                    tvSelectedNum.isVisible = it.isLoaded
                }
            }
        }

        fun bind(item: ImagePickerSelectorItem?) {

            binding?.run {
                item?.let { item ->
                    root.setOnClickListener {
                        onItemClick.invoke(adapterPosition)
                    }

                    cbSelect.isVisible = item.isLoaded
                    tvSelectedNum.isVisible = item.isLoaded

                    vSelectedRect.run {
                        if (item.isSelected) {
                            background =
                                ContextCompat.getDrawable(context, R.drawable.rect_selected)
                        } else {
                            background = null
                        }
                    }

                    stateVisible(item)

                    cbSelect.run {
                        setOnCheckedChangeListener(null)
                        isChecked = item.isSelected
                        setOnCheckedChangeListener { _, isChecked ->
                            item.isSelected = isChecked
                            onItemSelected(adapterPosition, isChecked)
                        }
                    }
                    item.img?.let {
                        ivImage.setUriImg(it) { failedUri ->
                            val findIndex = items.indexOfFirst { item ->
                                item.img == failedUri
                            }
                            if (findIndex != -1) {
                                setItemLoadFailed(findIndex)
                            }

                        }
                    }

                    executePendingBindings()
                }
            }
        }
    }

    companion object {
        const val NUM_REFRESH = 1
        const val STATE_REFRESH = 2
        const val CHECKER_VISIBLE_REFRESH = 3
    }
}