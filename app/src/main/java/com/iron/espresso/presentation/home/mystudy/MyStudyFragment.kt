package com.iron.espresso.presentation.home.mystudy

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.iron.espresso.R
import com.iron.espresso.ToolbarHelper
import com.iron.espresso.databinding.FragmentMystudyBinding
import com.iron.espresso.presentation.home.mystudy.adapter.MyStudyAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MyStudyFragment : Fragment() {

    private lateinit var binding: FragmentMystudyBinding

    private val myStudyViewModel by sharedViewModel<MyStudyViewModel>()

    private val myStudyAdapter by lazy { MyStudyAdapter() }

    private lateinit var toolbarHelper: ToolbarHelper


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mystudy, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbarHelper = ToolbarHelper((activity as AppCompatActivity), binding.appbar).apply {
            setTitle(TOOLBAR_TITLE)
        }

        binding.run {
            rvMyStudy.adapter = myStudyAdapter
            vm = myStudyViewModel
            myStudyViewModel.showMyStudyList()
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

        private const val TOOLBAR_TITLE = "내 스터디"

        fun newInstance() =
            MyStudyFragment()
    }
}