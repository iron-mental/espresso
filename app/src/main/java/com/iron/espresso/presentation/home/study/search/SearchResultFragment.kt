package com.iron.espresso.presentation.home.study.search

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iron.espresso.Logger
import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentSearchResultBinding
import com.iron.espresso.presentation.home.mystudy.MyStudyDetailActivity
import com.iron.espresso.presentation.home.study.StudyDetailActivity
import com.iron.espresso.presentation.home.study.adapter.StudyListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchResultFragment :
    BaseFragment<FragmentSearchResultBinding>(R.layout.fragment_search_result) {

    private val viewModel by viewModels<StudyResultViewModel>()
    val studyListAdapter = StudyListAdapter()
    private val editText: EditText?
        get() = baseActivity?.getCustomView()?.findViewById(R.id.edit_view) as? EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val keyword = arguments?.getString(SEARCH_KEYWORD).orEmpty()
        scrollListener()

        binding.run {
            studyList.adapter = studyListAdapter

            swipeRefresh.apply {
                setOnRefreshListener {
                    viewModel.showSearchStudyList(keyword)

                    this.isRefreshing = false
                }
            }
        }

        viewModel.run {
            showSearchStudyList(keyword)
            studyList.observe(viewLifecycleOwner, Observer { studyList ->
                studyListAdapter.setItemList(studyList)
            })
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
                startActivity(StudyDetailActivity.getIntent(requireContext(), studyItem.id))
            }
        }
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
                            viewModel.getSearchStudyListPaging(OPTION, studyListAdapter.itemCount)
                        }
                    }
                }
            }
        )
    }

    override fun onBackPressed(): Boolean {
        editText?.let { editText ->
            if (editText.text.isNotEmpty()) {
                editText.text.clear()
            }
        }

        val fragment =
            parentFragmentManager.fragments.findLast { it !is SearchResultFragment && it is BaseFragment<*> }

        parentFragmentManager.beginTransaction().remove(this).commit()
        if (fragment != null) {
            parentFragmentManager.beginTransaction().show(fragment).commit()
            return true
        }
        return super.onBackPressed()
    }

    companion object {
        private const val SEARCH_KEYWORD = "searchKeyword"
        private const val OPTION = "default"
        fun newInstance(searchKeyword: String) =
            SearchResultFragment().apply {
                arguments = Bundle().apply {
                    putString(SEARCH_KEYWORD, searchKeyword)
                }
            }
    }
}