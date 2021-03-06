package com.iron.espresso.presentation.home.mystudy.studydetail.notice

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iron.espresso.Logger
import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.data.model.AuthorityType
import com.iron.espresso.data.model.NoticeItem
import com.iron.espresso.databinding.FragmentNoticeBinding
import com.iron.espresso.presentation.home.mystudy.MyStudyDetailActivity
import com.iron.espresso.presentation.home.mystudy.adapter.NoticeAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoticeFragment : BaseFragment<FragmentNoticeBinding>(R.layout.fragment_notice) {

    private val noticeAdapter = NoticeAdapter()
    private var studyId = 0
    private var authority: String? = null

    private val viewModel by viewModels<NoticeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        studyId =
            arguments?.getInt(MyStudyDetailActivity.STUDY_ID) ?: MyStudyDetailActivity.DEFAULT_VALUE

        viewModel.showNoticeList(studyId)

        viewModel.noticeListItem.observe(viewLifecycleOwner, { noticeListItem ->
            Logger.d("$noticeListItem")
            noticeAdapter.setItemList(noticeListItem)
        })

        viewModel.scrollItem.observe(viewLifecycleOwner, {
            noticeAdapter.setScrollItem(it)
        })

        noticeAdapter.setItemClickListener { noticeItem: NoticeItem ->
            startActivityForResult(
                NoticeDetailActivity.getIntent(
                    requireContext(),
                    noticeItem.id,
                    studyId,
                    authority
                ), REQUEST_DETAIL_CODE
            )
        }
        binding.noticeList.adapter = noticeAdapter
        binding.viewModel = viewModel

        scrollListener()

    }

    fun setAuthority(authority: String) {
        this.authority = authority
        activity?.invalidateOptionsMenu()
    }

    private fun scrollListener() {
        binding.noticeList.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val linear =
                        binding.noticeList.layoutManager as LinearLayoutManager

                    if (linear.findLastCompletelyVisibleItemPosition()
                        == noticeAdapter.itemCount - 1
                    ) {
                        if (noticeAdapter.itemCount >= 10) {
                            viewModel.showNoticeListPaging()
                        }
                    }
                }
            }
        )
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

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        if (authority == AuthorityType.HOST.authority) {
            menu.findItem(R.id.create_notice).isVisible = true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.create_notice -> {
                startActivityForResult(
                    NoticeCreateActivity.getIntent(requireContext(), studyId),
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
                    putInt(MyStudyDetailActivity.STUDY_ID, data)
                }
            }
    }
}



