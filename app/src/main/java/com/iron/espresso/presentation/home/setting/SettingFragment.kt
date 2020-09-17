package com.iron.espresso.presentation.home.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.iron.espresso.R
import com.iron.espresso.databinding.FragmentSettingBinding
import kotlinx.android.synthetic.main.fragment_setting.*

class SettingFragment : Fragment() {

    private lateinit var binding: FragmentSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
        binding.lifecycleOwner = this

        val settingCategoryList = arrayListOf<CategoryItem>()
        for ((count, i) in resources.getStringArray(R.array.setting_category).withIndex()) {
            val arrayList = arrayListOf<ItemList>()
            when (count) {
                0 -> for (j in resources.getStringArray(R.array.category_account)) {
                    arrayList.add(ItemList(j))
                }
                1 -> for (j in resources.getStringArray(R.array.category_notice)) {
                    arrayList.add(ItemList(j))
                }
                2 -> for (j in resources.getStringArray(R.array.category_info)) {
                    arrayList.add(ItemList(j))
                }
                3 -> for (j in resources.getStringArray(R.array.category_etc)) {
                    arrayList.add(ItemList(j))
                }
            }
            settingCategoryList.add(CategoryItem(i, arrayList))
        }

        binding.settingRecyclerview.layoutManager = LinearLayoutManager(context)
        binding.settingRecyclerview.adapter = context?.let { SettingAdapter(it,settingCategoryList) }

        Glide.with(this).load(R.drawable.ic_launcher_background).circleCrop().into(binding.settingProfileImage)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun newInstance() =
            SettingFragment()
    }

}