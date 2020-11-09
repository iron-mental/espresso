package com.iron.espresso.presentation.home.mystudy.studydetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.iron.espresso.R
import com.iron.espresso.data.model.NoticeItemType
import com.iron.espresso.data.model.NoticeListItem
import com.iron.espresso.databinding.FragmentNoticeBinding
import com.iron.espresso.presentation.home.mystudy.adapter.NoticeAdapter

class NoticeFragment : Fragment() {

    private lateinit var binding: FragmentNoticeBinding
    private var noticeAdapter = NoticeAdapter()

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
            add(NoticeListItem("강남역 윙스터디1", "강\n남\n구\n청", "10/27", NoticeItemType.ITEM))
            add(NoticeListItem("강남역 윙스터디2", "강남구", "10/27", NoticeItemType.HEADER))
            add(NoticeListItem("강남역 윙스터디3", "강남구", "10/27", NoticeItemType.ITEM))
            add(NoticeListItem("강남역 윙스터디4", "강남구", "10/27", NoticeItemType.ITEM))
            add(NoticeListItem("강남역 윙스터디5", "강남구", "10/27", NoticeItemType.HEADER))
            add(NoticeListItem("강남역 윙스터디6", "강남구", "10/27", NoticeItemType.ITEM))
            add(NoticeListItem("강남역 윙스터디7", "강남구", "10/27", NoticeItemType.ITEM))
            add(NoticeListItem("강남역 윙스터디8", "강\n남\n구\n청", "10/27", NoticeItemType.ITEM))
            add(NoticeListItem("강남역 윙스터디9", "강남구", "10/27", NoticeItemType.HEADER))
            add(NoticeListItem("강남역 윙스터디10", "강남구", "10/27", NoticeItemType.ITEM))
            add(NoticeListItem("강남역 윙스터디11", "강남구", "10/27", NoticeItemType.ITEM))
            add(NoticeListItem("강남역 윙스터디12", "강남구", "10/27", NoticeItemType.HEADER))
            add(NoticeListItem("강남역 윙스터디13", "강남구", "10/27", NoticeItemType.ITEM))
            add(NoticeListItem("강남역 윙스터디14", "강남구", "10/27", NoticeItemType.ITEM))
        }

        noticeListItem.sortBy { it.type }

        noticeAdapter.setItemList(noticeListItem)
        binding.noticeList.adapter = noticeAdapter
        noticeAdapter.itemClickListener = { noticeItem ->
            startActivity(context?.let { NoticeDetailActivity.getInstance(it, noticeItem) })
        }
    }

    companion object {
        fun newInstance() =
            NoticeFragment()
    }
}



