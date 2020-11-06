package com.iron.espresso.presentation.home.setting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.switchmaterial.SwitchMaterial
import com.iron.espresso.R
import com.iron.espresso.databinding.FragmentSettingBinding
import com.iron.espresso.presentation.home.setting.adapter.SettingAdapter
import com.iron.espresso.presentation.home.setting.model.*
import com.iron.espresso.presentation.profile.ProfileActivity

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
        settingAdapter.setItemClickListener(object : SettingAdapter.ItemClickListener {
            override fun onClick(view: View, noticeSwitch: SwitchMaterial?) {

                Toast.makeText(context, view.tag.toString(), Toast.LENGTH_SHORT).show()
                when (view.tag) {
                    getString(R.string.push_alarm) -> {
                        noticeSwitch!!.isChecked = !noticeSwitch.isChecked
                    }
                    getString(R.string.notice) -> {
                        val intent = Intent(context, SettingNoticeActivity::class.java)
                        startActivity(intent)
                    }
                    getString(R.string.help) -> {
                        val intent = Intent(context, SettingHelpActivity::class.java)
                        startActivity(intent)
                    }
                    getString(R.string.contact) -> {
                        val intent = Intent(context, SettingContactActivity::class.java)
                        startActivity(intent)
                    }
                    getString(R.string.terms) -> {
                        val intent = Intent(context, SettingTermsActivity::class.java)
                        startActivity(intent)
                    }
                    getString(R.string.policy) -> {
                        val intent = Intent(context, SettingPolicyActivity::class.java)
                        startActivity(intent)
                    }
                    0 -> {
                        val intent = Intent(context, SettingProfileActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        })
        binding.settingRecyclerview.adapter = settingAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val testButton = Button(context).apply {
            text = "프로필 상세"
            setOnClickListener {
                startActivity(ProfileActivity.getInstance(context))
            }
        }

        (view as ViewGroup).addView(testButton)
    }

    companion object {
        fun newInstance() =
            SettingFragment()
    }
}