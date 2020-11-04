package com.iron.espresso.presentation.home.mystudy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.iron.espresso.R
import com.iron.espresso.databinding.FragmentMystudyBinding
import com.iron.espresso.presentation.home.mystudy.adapter.MyStudyAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MyStudyFragment : Fragment() {

    private lateinit var binding: FragmentMystudyBinding

    private val myStudyViewModel by sharedViewModel<MyStudyViewModel>()

    private val myStudyAdapter by lazy { MyStudyAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mystudy, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            rvMyStudy.adapter = myStudyAdapter
            vm = myStudyViewModel
            myStudyViewModel.showMyStudyList()

            myStudyAdapter.setItemClickListener(object : MyStudyAdapter.ItemClickListener {
                override fun onClick(view: View) {
                    Toast.makeText(context, view.tag.toString(), Toast.LENGTH_SHORT).show()
                    startActivity(context?.let {
                        StudyDetailActivity.getInstance(it).putExtra("title", view.tag.toString())
                    })
                }
            })
        }


    }

    companion object {
        fun newInstance() =
            MyStudyFragment()
    }
}