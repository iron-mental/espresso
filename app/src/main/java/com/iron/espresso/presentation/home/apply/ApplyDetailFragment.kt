package com.iron.espresso.presentation.home.apply

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.commit
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentApplyDetailBinding
import com.iron.espresso.ext.EventObserver
import com.iron.espresso.ext.setLoading
import com.iron.espresso.ext.setUrlImg
import com.iron.espresso.ext.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ApplyDetailFragment :
    BaseFragment<FragmentApplyDetailBinding>(R.layout.fragment_apply_detail) {

    private val viewModel by viewModels<ApplyDetailViewModel>()

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
                image.setUrlImg(it.image)
                title.text = it.title
                message.text = it.message
            }
        }
    }

    private fun setupViewModel() {
        with(viewModel) {
            modifiedEvent.observe(viewLifecycleOwner, EventObserver {

            })

            deletedEvent.observe(viewLifecycleOwner, EventObserver {
                onBackPressed()
            })

            toastMessage.observe(viewLifecycleOwner, EventObserver(::toast))
            loadingState.observe(viewLifecycleOwner, EventObserver(::setLoading))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_apply_detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.edit -> {

                return true
            }
            R.id.cancel -> {
                showApplyCancelDialog()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showApplyCancelDialog() {
        val dialog = ConfirmDialog.newInstance(getString(R.string.dialog_apply_cancel_title))

        dialog.show(parentFragmentManager, dialog::class.java.simpleName)

        dialog.setFragmentResultListener(dialog::class.java.simpleName) { requestKey: String, bundle: Bundle ->
            val result = bundle.get(ConfirmDialog.RESULT)

            if (result == Activity.RESULT_OK) {
                viewModel.requestDelete()
            }
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
        const val ARG_APPLY = "apply"

        fun newInstance(item: ApplyStudyItem): ApplyDetailFragment =
            ApplyDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_APPLY, item)
                }
            }
    }
}