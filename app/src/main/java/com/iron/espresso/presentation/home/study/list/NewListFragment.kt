package com.iron.espresso.presentation.home.study.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentNewListBinding
import com.iron.espresso.presentation.home.mystudy.MyStudyDetailActivity
import com.iron.espresso.presentation.home.study.StudyDetailActivity
import com.iron.espresso.presentation.home.study.adapter.StudyListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewListFragment : BaseFragment<FragmentNewListBinding>(R.layout.fragment_new_list) {

    private val viewModel by viewModels<StudyListViewModel>()
    private val studyListAdapter = StudyListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.studyList.adapter = studyListAdapter

        viewModel.getStudyList("android", SORT_NEW)

        viewModel.studyList.observe(viewLifecycleOwner, Observer { studyList ->
            studyListAdapter.setItemList(studyList)
        })

        viewModel.scrollItem.observe(viewLifecycleOwner, Observer { scrollItem ->
            studyListAdapter.setScrollItem(scrollItem, studyListAdapter.itemCount)
        })

        studyListAdapter.setItemClickListener { studyItem ->
            if (studyItem.isMember) {
                startActivity(MyStudyDetailActivity.getInstance(requireContext(), studyItem.title, studyItem.id))
            } else {
                startActivity(StudyDetailActivity.getInstance(requireContext(), studyItem.id))
            }
        }

        binding.swipeRefresh.apply {
            setOnRefreshListener {
                viewModel.getStudyList("android", SORT_NEW)

                this.isRefreshing = false
            }
        }

        scrollListener()
    }

    private fun scrollListener() {
        binding.studyList.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val linear =
                        binding.studyList.layoutManager as LinearLayoutManager

                    if (linear.findLastCompletelyVisibleItemPosition()
                        == studyListAdapter.itemCount - 1
                    ) {
                        viewModel.getStudyListPaging(studyListAdapter.itemCount)
                    }
                }
            }
        )
    }

    companion object {
        private const val SORT_NEW = "new"
        fun newInstance() =
            NewListFragment()
    }
}

