package com.iron.espresso.presentation.home.mystudy

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import com.iron.espresso.CountDrawable
import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.data.model.MyStudyItem
import com.iron.espresso.databinding.FragmentMystudyBinding
import com.iron.espresso.presentation.home.alert.AlertListActivity
import com.iron.espresso.presentation.home.apply.MyApplyStudyActivity
import com.iron.espresso.presentation.home.mystudy.adapter.MyStudyAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MyStudyFragment :
    BaseFragment<FragmentMystudyBinding>(R.layout.fragment_mystudy) {

    private val myStudyViewModel by viewModels<MyStudyViewModel>()

    private val myStudyAdapter by lazy { MyStudyAdapter() }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            viewModel = myStudyViewModel
            rvMyStudy.adapter = myStudyAdapter
            myStudyViewModel.showMyStudyList()
            swipeRefresh.apply {
                setOnRefreshListener {
                    myStudyViewModel.showMyStudyList()
                }
            }
        }

        myStudyViewModel.studyList.observe(viewLifecycleOwner, { studyList ->
            myStudyAdapter.replaceAll(studyList)
            if (binding.swipeRefresh.isRefreshing) {
                binding.swipeRefresh.isRefreshing = false
            }
        })

        myStudyViewModel.badgeCount.observe(viewLifecycleOwner, {
            activity?.invalidateOptionsMenu()
        })

        myStudyAdapter.setItemClickListener(object : MyStudyAdapter.ItemClickListener {
            override fun onClick(item: MyStudyItem) {
                startActivityForResult(
                    MyStudyDetailActivity.getInstance(requireContext(), item.title, item.id),
                    REQUEST_LEAVE_CODE
                )
            }
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_LEAVE_CODE && resultCode == RESULT_OK) {
            myStudyViewModel.showMyStudyList()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_mystudy, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val badgeCount = myStudyViewModel.badgeCount.value ?: 0
        setCount(menu, badgeCount)
        super.onPrepareOptionsMenu(menu)
    }

    private fun setCount(menu: Menu, count: Int) {
        val menuItem: MenuItem = menu.findItem(R.id.notify)
        val icon: LayerDrawable? = menuItem.icon as? LayerDrawable
        val reuse: Drawable? = icon?.findDrawableByLayerId(R.id.ic_group_count)

        val badge: CountDrawable = if (reuse is CountDrawable) {
            reuse
        } else {
            CountDrawable(requireContext())
        }
        badge.setCount(count.toString())
        icon?.mutate()
        icon?.setDrawableByLayerId(R.id.ic_group_count, badge)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.notify -> {
                startActivity(AlertListActivity.getIntent(requireContext()))
            }

            R.id.more -> {
                startActivity(MyApplyStudyActivity.getIntent(requireContext()))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val REQUEST_LEAVE_CODE = 1
        fun newInstance() =
            MyStudyFragment()
    }
}