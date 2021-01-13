package com.wswon.picker


import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.PagerAdapter
import com.wswon.picker.databinding.ItemImageDetailBinding

class ImageDetailAdapter : PagerAdapter() {

    private val items = mutableListOf<ImageDetailItem>()

    override fun isViewFromObject(view: View, obj: Any): Boolean =
        view == obj as View

    override fun getCount(): Int =
        items.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val inflater = container.context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val binding: ItemImageDetailBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_image_detail, container, false)

        binding.run {
            setVariable(BR.item, items[position])
            executePendingBindings()

            container.addView(root)
        }
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }

    fun replaceAll(items: List<ImageDetailItem>?) {
        items?.let {
            this.items.run {
                clear()
                addAll(it)
            }
            notifyDataSetChanged()
        }
    }
}