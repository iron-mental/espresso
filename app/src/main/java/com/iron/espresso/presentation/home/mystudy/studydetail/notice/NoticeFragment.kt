package com.iron.espresso.presentation.home.mystudy.studydetail.notice

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
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
            }, REQUEST_DETAIL_CODE)
        }

        binding.noticeList.adapter = noticeAdapter

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == REQUEST_DETAIL_CODE && resultCode == RESULT_OK) {
            viewModel.showNoticeList(studyId)
        } else if (requestCode == REQUEST_CREATE_CODE && resultCode == RESULT_OK) {
            viewModel.showNoticeList(studyId)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_notice, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                activity?.finish()
            }
            R.id.create_notice -> {
                startActivityForResult(
                    NoticeCreateActivity.getInstance(requireContext(), studyId),
                    REQUEST_CREATE_CODE
                )
            }

            else -> {
                Toast.makeText(context, "${item.title}", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }

    companion object {
        private const val REQUEST_DETAIL_CODE = 1
        private const val REQUEST_CREATE_CODE = 2
        fun newInstance(data: Int) =
            NoticeFragment().apply {
                arguments = Bundle().apply {
                    putInt(STUDY_ID, data)
                }
            }
    }
}



