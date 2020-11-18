package com.iron.espresso.presentation.home.study

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.iron.espresso.R
import com.iron.espresso.base.ToolbarHelper
import com.iron.espresso.databinding.FragmentStudyBinding
import com.iron.espresso.presentation.StudyCategoryItem
import com.iron.espresso.presentation.home.study.adapter.CategoryAdapter
import com.iron.espresso.presentation.home.study.adapter.viewholder.StudyCategoryAdapterListener

class StudyFragment : Fragment(), StudyCategoryAdapterListener {
    private lateinit var binding: FragmentStudyBinding

    private lateinit var toolbarHelper: ToolbarHelper

    private val categoryAdapter by lazy { CategoryAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_study, container, false)
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun getData(item: StudyCategoryItem, imageView: ImageView) {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button = Button(context).apply {
            text = "스터디 리스트"
            setOnClickListener {
                startActivity(StudyListActivity.getInstance(context))
            }
        }
        (view as ViewGroup).addView(button)

        toolbarHelper = ToolbarHelper((activity as AppCompatActivity), binding.appbar).apply {
            setToolbarTitle(TOOLBAR_TITLE)
        }

        binding.apply {
            viewStudyCategory.adapter = categoryAdapter
            viewStudyCategory.layoutManager = GridLayoutManager(context, SPAN_COUNT)
            categoryAdapter.addAll(DUMMY_DATA)
        }
        categoryAdapter.setItemClickListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_study, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search_study -> {
                val intent = Intent(context, SearchStudyActivity::class.java)
                startActivity(intent)
            }

            R.id.add_study -> {
                startActivity(StudyCategoryActivity.getInstance(requireContext()))
            }

        }
        return super.onOptionsItemSelected(item)
    }


    companion object {

        private const val TOOLBAR_TITLE = "스터디"

        private const val SPAN_COUNT = 2

        val DUMMY_DATA = mutableListOf<StudyCategoryItem>().apply {
            for (i in 0 until 3) {
                add(StudyCategoryItem(R.drawable.android))
                add(StudyCategoryItem(R.drawable.swift))
                add(StudyCategoryItem(R.drawable.node))
                add(StudyCategoryItem(R.drawable.frontend))
                add(StudyCategoryItem(R.drawable.tenserflow))
            }
        }

        fun newInstance() =
            StudyFragment()
    }
}
