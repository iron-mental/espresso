package com.iron.espresso.presentation.home.apply

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.commit
import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentApplyDetailBinding
import com.iron.espresso.ext.setUrlImg
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ApplyDetailFragment :
    BaseFragment<FragmentApplyDetailBinding>(R.layout.fragment_apply_detail) {

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

                return true
            }
        }

        return super.onOptionsItemSelected(item)
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
        private const val ARG_APPLY = "apply"

        fun newInstance(item: ApplyStudyItem): ApplyDetailFragment =
            ApplyDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_APPLY, item)
                }
            }
    }
}