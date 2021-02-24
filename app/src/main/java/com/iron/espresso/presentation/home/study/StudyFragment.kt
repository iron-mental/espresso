package com.iron.espresso.presentation.home.study

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentStudyBinding
import com.iron.espresso.presentation.home.study.adapter.CategoryAdapter
import com.iron.espresso.presentation.home.study.adapter.viewholder.StudyCategoryAdapterListener
import com.iron.espresso.presentation.home.study.list.StudyListActivity
import com.iron.espresso.presentation.home.study.search.SearchStudyActivity

class StudyFragment :
    BaseFragment<FragmentStudyBinding>(R.layout.fragment_study),
    StudyCategoryAdapterListener {

    private val categoryAdapter by lazy { CategoryAdapter() }
    private val viewModel by viewModels<StudyViewModel>()

    override fun getData(item: String, imageView: ImageView) {
        startActivity(StudyListActivity.getIntent(requireContext(), item))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            viewStudyCategory.adapter = categoryAdapter
            viewStudyCategory.layoutManager = GridLayoutManager(context, SPAN_COUNT)
        }
        categoryAdapter.setItemClickListener(this)

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
