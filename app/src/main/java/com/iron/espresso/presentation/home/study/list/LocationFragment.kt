package com.iron.espresso.presentation.home.study.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentLocationBinding
import com.iron.espresso.presentation.home.mystudy.MyStudyDetailActivity
import com.iron.espresso.presentation.home.study.StudyDetailActivity
import com.iron.espresso.presentation.home.study.adapter.StudyListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationFragment : BaseFragment<FragmentLocationBinding>(R.layout.fragment_location) {

    private val viewModel by viewModels<StudyListViewModel>()
    private val studyListAdapter = StudyListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val category = arguments?.getString(StudyListActivity.STUDY_CATEGORY).orEmpty()
        binding.studyList.adapter = studyListAdapter

        viewModel.getStudyList(category, SORT_LENGTH)

        viewModel.studyList.observe(viewLifecycleOwner, Observer { studyList ->
            studyListAdapter.setItemList(studyList)
        })

        studyListAdapter.setItemClickListener { studyItem ->
            if (studyItem.isMember) {
                startActivity(MyStudyDetailActivity.getInstance(requireContext(), studyItem.title, studyItem.id))
            } else {
                startActivity(StudyDetailActivity.getIntent(requireContext(), studyItem.id))
            }
        }

        binding.swipeRefresh.apply {
            setOnRefreshListener {
                viewModel.getStudyList("android", SORT_LENGTH)

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

                    val layoutManager =
                        binding.studyList.layoutManager as LinearLayoutManager

                    if (layoutManager.findLastCompletelyVisibleItemPosition()
                        == studyListAdapter.itemCount - 1
                    ) {
                        viewModel.getStudyListPaging(OPTION, studyListAdapter.itemCount)
                    }
                }
            }
        )
    }

    companion object {
        private const val SORT_LENGTH = "length"
        private const val OPTION = "distance"
        fun newInstance(category: String) =
            LocationFragment().apply {
                arguments = Bundle().apply {
                    putString(StudyListActivity.STUDY_CATEGORY, category)
                }
            }
    }
}

