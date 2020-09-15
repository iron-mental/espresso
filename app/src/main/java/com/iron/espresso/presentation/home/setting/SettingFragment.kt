package com.iron.espresso.presentation.home.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.iron.espresso.R
import com.iron.espresso.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {

    private lateinit var binding: FragmentSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
        binding.lifecycleOwner = this

        val itemList = arrayListOf<ItemList>()

        val settingCategoryList = arrayListOf<CategoryItem>()
        for (i in resources.getStringArray(R.array.setting_category)) {
            itemList.add(ItemList(i))
            settingCategoryList.add(CategoryItem(i,itemList))
        }

        binding.settingRecyclerview.layoutManager = LinearLayoutManager(context)
        binding.settingRecyclerview.adapter = SettingAdapter(settingCategoryList)


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