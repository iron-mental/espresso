package com.iron.espresso.presentation.home.mystudy.studydetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.iron.espresso.R
import com.iron.espresso.data.model.NoticeListItem
import com.iron.espresso.databinding.FragmentNoticeBinding
import com.iron.espresso.presentation.home.mystudy.adapter.NoticeAdapter

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

        val noticeList = mutableListOf<NoticeListItem>().apply {
            add(NoticeListItem("공지", "강남역 윙스터디", "강남구", "10/27"))
            add(NoticeListItem("공지", "강남역 윙스터디", "강남구", "10/27"))
            add(NoticeListItem("공지", "강남역 윙스터디", "강남구", "10/27"))
            add(NoticeListItem("공지", "강남역 윙스터디", "강남구", "10/27"))
            add(NoticeListItem("공지", "강남역 윙스터디", "강남구", "10/27"))
            add(NoticeListItem("공지", "강남역 윙스터디", "강남구", "10/27"))
            add(NoticeListItem("공지", "강남역 윙스터디", "강남구", "10/27"))
        }

        binding.noticeList.adapter = NoticeAdapter(noticeList)
    }

    companion object {
        fun newInstance() =
            NoticeFragment()
    }
}



