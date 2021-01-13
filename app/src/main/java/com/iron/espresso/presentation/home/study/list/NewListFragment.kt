package com.iron.espresso.presentation.home.study.list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentNewListBinding
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

            studyListAdapter.apply {
                setItemList(studyList)
                itemClickListener = { title ->
                    Toast.makeText(
                        context,
                        "onClick title: $title",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })

        viewModel.scrollItem.observe(viewLifecycleOwner, Observer { scrollItem ->
            studyListAdapter.setScrollItem(scrollItem)
        })

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
                        if (studyListAdapter.itemCount >= 10) {
                            viewModel.getStudyListPaging()
                        }
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

