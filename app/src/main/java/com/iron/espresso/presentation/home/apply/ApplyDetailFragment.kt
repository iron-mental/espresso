package com.iron.espresso.presentation.home.apply

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.commit
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

    private val viewModel by viewModels<ApplyDetailViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        setupViewModel()
        viewModel.getApplyDetail()
    }

    private fun setupView() {
        with(binding) {
            binding.viewModel = this@ApplyDetailFragment.viewModel
        }
    }

    private fun setupViewModel() {
        with(viewModel) {
            showLinkEvent.observe(viewLifecycleOwner, EventObserver { url ->
                if (url.startsWith("http://") || url.startsWith("https://")) {
                    CustomTabsIntent.Builder()
                        .build()
                        .launchUrl(requireContext(), Uri.parse(url))
                }
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

        fun newInstance(studyId: Int, applyId: Int, userId: Int): ApplyDetailFragment =
            ApplyDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(ApplyDetailViewModel.KEY_STUDY_ID, studyId)
                    putInt(ApplyDetailViewModel.KEY_APPLY_ID, applyId)
                    putInt(ApplyDetailViewModel.KEY_USER_ID, userId)
                }
            }
    }
}