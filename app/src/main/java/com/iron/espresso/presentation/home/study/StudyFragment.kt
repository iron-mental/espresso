package com.iron.espresso.presentation.home.study

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentStudyBinding
import com.iron.espresso.presentation.home.study.adapter.CategoryAdapter
import com.iron.espresso.presentation.home.study.list.StudyListActivity
import com.iron.espresso.presentation.home.study.search.SearchStudyActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StudyFragment :
    BaseFragment<FragmentStudyBinding>(R.layout.fragment_study) {

    private val categoryAdapter by lazy {
        CategoryAdapter { item ->
            startActivity(StudyListActivity.getIntent(requireContext(), item))
        }
    }
    private val viewModel by viewModels<StudyViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            viewStudyCategory.adapter = categoryAdapter
            viewStudyCategory.layoutManager = GridLayoutManager(context, SPAN_COUNT)
        }

        viewModel.getStudyCategory()
        viewModel.categoryList.observe(viewLifecycleOwner, { categoryList ->
            categoryAdapter.addAll(categoryList)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_study, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search_study -> {
                startActivity(SearchStudyActivity.getIntent(requireContext()))
            }

            R.id.add_study -> {
                startActivity(StudyCategoryActivity.getIntent(requireContext()))
            }

        }
        return super.onOptionsItemSelected(item)
    }


    companion object {
        private const val SPAN_COUNT = 2

        fun newInstance() =
            StudyFragment()
    }
}
