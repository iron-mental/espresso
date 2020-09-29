package com.iron.espresso.presentation.home.setting

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.iron.espresso.R
import com.iron.espresso.databinding.FragmentSettingBinding
import com.iron.espresso.presentation.home.setting.adapter.SettingAdapter
import com.iron.espresso.presentation.home.setting.model.*

class SettingFragment : Fragment() {

    private lateinit var binding: FragmentSettingBinding
    private lateinit var settingAdapter: SettingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
        binding.lifecycleOwner = this

        val settingList = arrayListOf<ItemType>()
        settingList.add(HeaderItem("", "", "", ""))

        resources.getStringArray(R.array.setting_category).forEachIndexed { count, category ->
            val itemList = arrayListOf<SettingItem>()
            when (count) {
                0 -> itemList.addAll(
                    resources.getStringArray(R.array.category_account).map {
                        SettingItem(it, SubItemType.IMAGE)
                    })
                1 -> itemList.addAll(
                    resources.getStringArray(R.array.category_notice).map {
                        SettingItem(it, SubItemType.SWITCH)
                    })
                2 -> itemList.addAll(
                    resources.getStringArray(R.array.category_info).map {
                        SettingItem(it, SubItemType.INFO)
                    })
                3 -> itemList.addAll(
                    resources.getStringArray(R.array.category_etc).map {
                        SettingItem(it, SubItemType.NONE)
                    })
            }
            settingList.add(SettingHeaderItem(category))
            settingList.addAll(itemList)
        }

        settingAdapter = SettingAdapter(settingList)
        binding.settingRecyclerview.adapter = settingAdapter

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