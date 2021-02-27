package com.iron.espresso.presentation.home.apply

import android.os.Bundle
import android.view.View
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentApplyListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ApplyListFragment : BaseFragment<FragmentApplyListBinding>(R.layout.fragment_apply_list) {

    private val viewModel by viewModels<MyApplyStudyViewModel>()

    private val adapter: MyApplyStudyAdapter by lazy {
        MyApplyStudyAdapter { item ->
            showApplyDetail(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        baseActivity?.setToolbarTitle(R.string.title_apply_my_study)
        baseActivity?.setNavigationIcon(R.drawable.ic_back_24)
        setupView()
        setupViewModel()
    }

    private fun setupView() {
        with(binding) {
            applyList.adapter = adapter
        }
    }

    private fun setupViewModel() {
        with(viewModel) {
            applyList.observe(viewLifecycleOwner, { list ->
                adapter.submitList(list)
            })
        }
    }

    private fun showApplyDetail(item: ApplyStudyItem) {
        val fragment = ApplyDetailFragment.newInstance(item)
        parentFragmentManager.commit {
            hide(this@ApplyListFragment)
            add(R.id.container, fragment)
        }
    }
}