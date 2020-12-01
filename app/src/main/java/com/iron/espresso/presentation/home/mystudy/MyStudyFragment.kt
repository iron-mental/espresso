package com.iron.espresso.presentation.home.mystudy

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentMystudyBinding
import com.iron.espresso.model.response.study.MyStudyResponse
import com.iron.espresso.presentation.home.mystudy.adapter.MyStudyAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MyStudyFragment :
    BaseFragment<FragmentMystudyBinding>(R.layout.fragment_mystudy) {

    private val myStudyViewModel by sharedViewModel<MyStudyViewModel>()

    private val myStudyAdapter by lazy { MyStudyAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            rvMyStudy.adapter = myStudyAdapter
            vm = myStudyViewModel
            myStudyViewModel.showMyStudyList()

            myStudyAdapter.setItemClickListener(object : MyStudyAdapter.ItemClickListener {
                override fun onClick(view: View) {
                    startActivity(context?.let {
                        StudyDetailActivity.getInstance(it, view.tag as MyStudyResponse)
                    })
                }
            })
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_mystudy, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.notify -> {
            }

            R.id.more -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun newInstance() =
            MyStudyFragment()
    }
}