package com.iron.espresso.presentation.home.study.list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentLocationBinding
import com.iron.espresso.presentation.home.study.StudyDetailActivity
import com.iron.espresso.presentation.home.study.adapter.StudyListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationFragment : BaseFragment<FragmentLocationBinding>(R.layout.fragment_location) {

    private val viewModel by viewModels<StudyListViewModel>()
    private val studyListAdapter = StudyListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.studyList.adapter = studyListAdapter

        viewModel.getStudyList("android", SORT_LENGTH)

        viewModel.studyList.observe(viewLifecycleOwner, Observer { studyList ->

            studyListAdapter.apply {
                setItemList(studyList)
                itemClickListener = { title ->
                    Toast.makeText(
                        context,
                        "onClick title: $title",
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(StudyDetailActivity.getInstance(requireContext()))

                }
            }
        })

        binding.swipeRefresh.apply {
            setOnRefreshListener {
                viewModel.getStudyList("android", SORT_LENGTH)

                this.isRefreshing = false
            }
        }
    }

    companion object {
        private const val SORT_LENGTH = "length"
        fun newInstance() =
            LocationFragment()
    }
}

