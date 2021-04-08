package com.iron.espresso.presentation.home.apply

import android.os.Bundle
import android.view.View
import androidx.fragment.app.commit
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentApplyListBinding
import com.iron.espresso.ext.EventObserver
import com.iron.espresso.ext.toast
import com.iron.espresso.presentation.home.apply.ApplyStudyListViewModel.Companion.KEY_STUDY_ID
import com.iron.espresso.presentation.home.apply.ApplyStudyListViewModel.Companion.KEY_TYPE
import dagger.hilt.android.AndroidEntryPoint
import java.util.Timer
import kotlin.concurrent.schedule

@AndroidEntryPoint
class ApplyListFragment : BaseFragment<FragmentApplyListBinding>(R.layout.fragment_apply_list) {

    private val listViewModel by viewModels<ApplyStudyListViewModel>()

    private val type: Type
        get() = listViewModel.type

    private val adapter: ApplyStudyAdapter by lazy {
        ApplyStudyAdapter(type) { item ->
            if (type == Type.NONE) showApplyDetail(
                listViewModel.studyId,
                item.id,
                item.userId
            ) else showMyApplyDetail(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        baseActivity?.setToolbarTitle(if (type == Type.NONE) R.string.title_apply_study else R.string.title_apply_my_study)
        baseActivity?.setNavigationIcon(R.drawable.ic_back_24)
        setupView()
        setupViewModel()
    }

    private fun setupView() {
        with(binding) {
            viewModel = listViewModel
            applyList.adapter = adapter

            emptyView.setMessage(getString(listViewModel.emptyViewMessage))
        }
    }

    private fun setupViewModel() {
        with(listViewModel) {
            applyList.observe(viewLifecycleOwner, { list ->
                adapter.submitList(list)
            })
            failureEvent.observe(viewLifecycleOwner, EventObserver { message ->
                Timer().schedule(500) {
                    activity?.runOnUiThread {
                        activity?.onBackPressed()
                        toast(message)
                    }
                }
            })
        }
    }

    private fun showApplyDetail(studyId: Int, applyId: Int, userId: Int) {
        val fragment = ApplyDetailFragment.newInstance(studyId, applyId, userId)
        parentFragmentManager.commit {
            hide(this@ApplyListFragment)
            add(R.id.container, fragment)
        }

        fragment.setFragmentResultListener(fragment::class.java.simpleName) { _: String, bundle: Bundle ->
            if (bundle.containsKey(ApplyDetailFragment.REFRESH)) {
                listViewModel.getList()
            }
        }
    }

    private fun showMyApplyDetail(item: ApplyStudyItem) {
        val fragment = MyApplyDetailFragment.newInstance(item)
        parentFragmentManager.commit {
            hide(this@ApplyListFragment)
            add(R.id.container, fragment)
        }

        fragment.setFragmentResultListener(fragment::class.java.simpleName) { _: String, bundle: Bundle ->
            if (bundle.containsKey(MyApplyDetailFragment.REFRESH)) {
                listViewModel.getList()
            }
        }
    }

    enum class Type {
        MY, NONE
    }

    companion object {

        fun newInstance(type: Type, studyId: Int = -1): ApplyListFragment =
            ApplyListFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_TYPE, type)
                    putInt(KEY_STUDY_ID, studyId)
                }
            }
    }
}