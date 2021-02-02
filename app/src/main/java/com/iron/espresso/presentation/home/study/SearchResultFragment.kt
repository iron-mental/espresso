package com.iron.espresso.presentation.home.study

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentSearchResultBinding
import com.iron.espresso.presentation.home.mystudy.MyStudyDetailActivity
import com.iron.espresso.presentation.home.study.adapter.StudyListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchResultFragment :
    BaseFragment<FragmentSearchResultBinding>(R.layout.fragment_search_result) {

    private val viewModel by viewModels<SearchStudyViewModel>()
    private val studyListAdapter = StudyListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val keyword = arguments?.getString(SEARCH_KEYWORD).orEmpty()

        viewModel.showSearchStudyList(keyword)

        binding.studyList.adapter = studyListAdapter

        binding.swipeRefresh.apply {
            setOnRefreshListener {
                viewModel.showSearchStudyList(keyword)

                this.isRefreshing = false
            }
        }

        viewModel.studyList.observe(viewLifecycleOwner, Observer { studyList ->
            studyListAdapter.setItemList(studyList)
        })

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
                startActivity(StudyDetailActivity.getIntent(requireContext(), studyItem.id))
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
                        if (studyListAdapter.itemCount >= 10) {
                            viewModel.getSearchStudyListPaging("new", studyListAdapter.itemCount)
                        }
                    }
                }
            }
        )
    }

//    override fun onBackPressed() {
//        if (binding.searchContainer.visibility == View.VISIBLE) {
//            super.onBackPressed()
//        } else {
//            binding.searchContainer.visibility = View.VISIBLE
//            binding.studyList.visibility = View.GONE
//            searchEditText.setText("")
//        }
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
            }
        }
        return true
    }

    companion object {
        private const val SEARCH_KEYWORD = "searchKeyword"
        fun newInstance(searchKeyword: String) =
            SearchResultFragment().apply {
                arguments = Bundle().apply {
                    putString(SEARCH_KEYWORD, searchKeyword)
                }
            }
    }
}