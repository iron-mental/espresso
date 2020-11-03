package com.iron.espresso.presentation.home.mystudy.studydetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.iron.espresso.R
import com.iron.espresso.data.model.NoticeItemType
import com.iron.espresso.data.model.NoticeListItem
import com.iron.espresso.databinding.FragmentNoticeBinding
import com.iron.espresso.presentation.home.mystudy.adapter.NoticeAdapter
import com.iron.espresso.presentation.home.setting.adapter.SettingAdapter
import com.iron.espresso.presentation.home.setting.model.*
import kotlinx.android.synthetic.main.item_notice_layout.*

class NoticeFragment : Fragment() {
    private lateinit var binding: FragmentNoticeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notice, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val noticeListItem = mutableListOf<NoticeListItem>().apply {
            add(NoticeListItem("강남역 윙스터디1", "강남구", "10/27", NoticeItemType.ITEM))
            add(NoticeListItem("강남역 윙스터디2", "강남구", "10/27", NoticeItemType.HEADER))
            add(NoticeListItem("강남역 윙스터디3", "강남구", "10/27", NoticeItemType.ITEM))
            add(NoticeListItem("강남역 윙스터디4", "강남구", "10/27", NoticeItemType.ITEM))
            add(NoticeListItem("강남역 윙스터디5", "강남구", "10/27", NoticeItemType.HEADER))
            add(NoticeListItem("강남역 윙스터디6", "강남구", "10/27", NoticeItemType.ITEM))
            add(NoticeListItem("강남역 윙스터디7", "강남구", "10/27", NoticeItemType.ITEM))
        }
        noticeListItem.sortBy { it.type }

        binding.noticeList.adapter = NoticeAdapter(noticeListItem)
    }

    companion object {
        fun newInstance() =
            NoticeFragment()
    }
}



