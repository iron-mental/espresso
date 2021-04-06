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
import com.iron.espresso.ext.EventObserver
import com.iron.espresso.ext.setLoading
import com.iron.espresso.presentation.home.mystudy.MyStudyDetailActivity
import com.iron.espresso.presentation.home.study.StudyDetailActivity
import com.iron.espresso.presentation.home.study.adapter.StudyListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewListFragment : BaseFragment<FragmentNewListBinding>(R.layout.fragment_new_list) {

    private val listViewModel by viewModels<StudyListViewModel>()
    private val studyListAdapter = StudyListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val category = arguments?.getString(STUDY_CATEGORY).orEmpty()
        binding.run {
            viewModel = listViewModel
            studyList.adapter = studyListAdapter
            swipeRefresh.apply {
                setOnRefreshListener {
                    listViewModel.getStudyList(category, SORT_NEW)

                    this.isRefreshing = false
                }
            }
        }

        listViewModel.run {
            getStudyList(category, SORT_NEW)

            studyList.observe(viewLifecycleOwner, Observer { studyList ->
                studyListAdapter.setItemList(studyList)
            })

            loadingState.observe(viewLifecycleOwner, EventObserver(::setLoading))
        }

        studyListAdapter.setItemClickListener { studyItem ->
            if (studyItem.isMember) {
                startActivity(
                    MyStudyDetailActivity.getInstance(
                        requireContext(),
                        studyItem.title,
                        studyItem.id
                    )
                )
            } else {
                startActivity(StudyDetailActivity.getIntent(requireContext(), studyItem.id, studyItem.title))
            }
        }

        scrollListener()
    }

    private fun scrollListener() {
        binding.studyList.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val layoutManager =
                        binding.studyList.layoutManager as LinearLayoutManager

                    if (layoutManager.findLastCompletelyVisibleItemPosition()
                        == studyListAdapter.itemCount - 1
                    ) {
                        listViewModel.getStudyListPaging(OPTION, studyListAdapter.itemCount)
                    }
                }
            }
        )
    }

    companion object {
        private const val SORT_NEW = "new"
        private const val OPTION = "default"
        private const val STUDY_CATEGORY = "study_category"
        fun newInstance(category: String) =
            NewListFragment().apply {
                arguments = Bundle().apply {
                    putString(STUDY_CATEGORY, category)
                }
            }
    }
}

