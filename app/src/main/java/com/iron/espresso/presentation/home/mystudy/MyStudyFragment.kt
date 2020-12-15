package com.iron.espresso.presentation.home.mystudy

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentMystudyBinding
import com.iron.espresso.model.response.study.MyStudyResponse
import com.iron.espresso.presentation.home.mystudy.adapter.MyStudyAdapter

class MyStudyFragment :
    BaseFragment<FragmentMystudyBinding>(R.layout.fragment_mystudy) {

    private val myStudyViewModel by viewModels<MyStudyViewModel>()

    private val myStudyAdapter by lazy { MyStudyAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            rvMyStudy.adapter = myStudyAdapter
            vm = myStudyViewModel
            myStudyViewModel.showMyStudyList()

            myStudyViewModel.studyList.observe(viewLifecycleOwner, Observer { studyList ->
                myStudyAdapter.replaceAll(studyList)
            })

            myStudyAdapter.setItemClickListener(object : MyStudyAdapter.ItemClickListener {
                override fun onClick(item: MyStudyResponse) {
                    startActivity(context?.let {
                        MyStudyDetailActivity.getInstance(it, item.title, item.id)
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