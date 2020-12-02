package com.iron.espresso.presentation.home.mystudy.studydetail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentNoticeBinding
import com.iron.espresso.model.response.notice.NoticeListResponse
import com.iron.espresso.model.response.notice.NoticeResponse
import com.iron.espresso.presentation.home.mystudy.StudyDetailActivity.Companion.DEFAULT_VALUE
import com.iron.espresso.presentation.home.mystudy.StudyDetailActivity.Companion.STUDY_ID
import com.iron.espresso.presentation.home.mystudy.adapter.NoticeAdapter

class NoticeFragment : BaseFragment<FragmentNoticeBinding>(R.layout.fragment_notice) {

    private val noticeAdapter = NoticeAdapter()
    private lateinit var noticeList: NoticeListResponse

    private val viewModel by lazy {
        requireActivity().viewModels<NoticeViewModel>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val studyId = arguments?.getInt(STUDY_ID) ?: DEFAULT_VALUE

        viewModel.value.showNoticeList(studyId)

        viewModel.value.noticeListItem.observe(viewLifecycleOwner, { noticeListItem ->
            noticeList = noticeListItem
            noticeAdapter.run {
                setItemList(noticeList)
            }
        })

        noticeAdapter.setItemClickListener { noticeItem: NoticeResponse ->
            Toast.makeText(context, "onClick position: $noticeItem", Toast.LENGTH_SHORT).show()
            startActivity(context?.let { NoticeDetailActivity.getInstance(it) })
        }

        binding.noticeList.adapter = noticeAdapter

    }

    companion object {
        fun newInstance(data: Int) =
            NoticeFragment().apply {
                arguments = Bundle().apply {
                    putInt(STUDY_ID, data)
                }
            }
    }
}



