package com.iron.espresso.presentation.home.mystudy.studydetail

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
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
    private var studyId = 0

    private val viewModel by viewModels<NoticeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        studyId = arguments?.getInt(STUDY_ID) ?: DEFAULT_VALUE

        viewModel.showNoticeList(studyId)

        viewModel.noticeListItem.observe(viewLifecycleOwner, { noticeListItem ->
            if (noticeListItem != null) {
                noticeList = noticeListItem
                noticeAdapter.run {
                    setItemList(noticeList)
                }
            } else {
                binding.emptyView.isVisible = true
                binding.noticeList.isVisible = false
            }
        })

        noticeAdapter.setItemClickListener { noticeItem: NoticeResponse ->
            startActivityForResult(context?.let {
                NoticeDetailActivity.getInstance(
                    it,
                    noticeItem.id,
                    studyId
                )
            }, REQUEST_CODE)
        }

        binding.noticeList.adapter = noticeAdapter

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            viewModel.showNoticeList(studyId)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_notice, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    companion object {
        private const val REQUEST_CODE = 1
        fun newInstance(data: Int) =
            NoticeFragment().apply {
                arguments = Bundle().apply {
                    putInt(STUDY_ID, data)
                }
            }
    }
}



