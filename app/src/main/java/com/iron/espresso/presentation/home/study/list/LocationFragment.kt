package com.iron.espresso.presentation.home.study.list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentNewListBinding
import com.iron.espresso.presentation.home.study.adapter.StudyListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationFragment : BaseFragment<FragmentNewListBinding>(R.layout.fragment_new_list) {

    private val viewModel by viewModels<StudyListViewModel>()
    private val studyListAdapter = StudyListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.studyList.adapter = studyListAdapter

        viewModel.getStudyList("android", "length")

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
    }

    companion object {
        fun newInstance() =
            LocationFragment()
    }
}

