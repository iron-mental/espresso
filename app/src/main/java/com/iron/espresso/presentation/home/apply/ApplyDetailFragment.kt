package com.iron.espresso.presentation.home.apply

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentApplyDetailBinding
import com.iron.espresso.ext.EventObserver
import com.iron.espresso.ext.setLoading
import com.iron.espresso.ext.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ApplyDetailFragment :
    BaseFragment<FragmentApplyDetailBinding>(R.layout.fragment_apply_detail) {

    private val viewModel by viewModels<MyApplyDetailViewModel>()

    private val applyStudyItem: ApplyStudyItem? by lazy {
        arguments?.getParcelable(ARG_APPLY)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        setupViewModel()
    }

    private fun setupView() {
        with(binding) {
            applyStudyItem?.let {

            }
        }
    }

    private fun setupViewModel() {
        with(viewModel) {
            modifiedEvent.observe(viewLifecycleOwner, EventObserver {
                setFragmentResult(
                    this@ApplyDetailFragment::class.java.simpleName,
                    bundleOf(REFRESH to "")
                )
            })

            deletedEvent.observe(viewLifecycleOwner, EventObserver {
                setFragmentResult(
                    this@ApplyDetailFragment::class.java.simpleName,
                    bundleOf(REFRESH to "")
                )
                onBackPressed()
            })

            toastMessage.observe(viewLifecycleOwner, EventObserver(::toast))
            loadingState.observe(viewLifecycleOwner, EventObserver(::setLoading))
        }
    }

    override fun onBackPressed(): Boolean {
        val prevFragment =
            parentFragmentManager.fragments.findLast { it is BaseFragment<*> && it !is ApplyDetailFragment }

        parentFragmentManager.commit {
            remove(this@ApplyDetailFragment)
            if (prevFragment != null) {
                show(prevFragment)
            }
        }

        return prevFragment != null
    }

    companion object {
        const val REFRESH = "refresh"
        const val ARG_APPLY = "apply"

        fun newInstance(item: ApplyStudyItem): MyApplyDetailFragment =
            MyApplyDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_APPLY, item)
                }
            }
    }
}