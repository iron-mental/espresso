package com.iron.espresso.presentation.home.mystudy.studydetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentNoticeBinding
import com.iron.espresso.model.response.notice.NoticeListResponse
import com.iron.espresso.model.response.notice.NoticeResponse
import com.iron.espresso.presentation.home.mystudy.StudyDetailActivity
import com.iron.espresso.presentation.home.mystudy.adapter.NoticeAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoticeFragment : BaseFragment<FragmentNoticeBinding>(R.layout.fragment_notice) {

    private val noticeAdapter = NoticeAdapter()
    private lateinit var noticeList: NoticeListResponse

    private val viewModel by viewModels<NoticeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val studyId =
            arguments?.getInt(StudyDetailActivity.STUDY_ID) ?: StudyDetailActivity.DEFAULT_VALUE

        viewModel.showNoticeList(studyId)

        viewModel.noticeListItem.observe(viewLifecycleOwner, { noticeListItem ->
            if (noticeListItem != null) {
                noticeList = noticeListItem
                noticeAdapter.run {
                    setItemList(noticeList)
                }
            }
        })

        noticeAdapter.setItemClickListener { noticeItem: NoticeResponse ->
            startActivity(
                NoticeDetailActivity.getInstance(requireContext(), noticeItem.id, studyId)
            )
        }

        binding.noticeList.adapter = noticeAdapter

    }

    companion object {
        fun newInstance(data: Int) =
            NoticeFragment().apply {
                arguments = Bundle().apply {
                    putInt(StudyDetailActivity.STUDY_ID, data)
                }
            }
    }
}



