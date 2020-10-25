package com.iron.espresso.presentation.home.study

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.iron.espresso.R
import com.iron.espresso.databinding.ActivityCategoryStudyBinding
import com.iron.espresso.presentation.StudyCategoryItem
import com.iron.espresso.presentation.home.study.adapter.CategoryAdapter
import com.iron.espresso.presentation.home.study.adapter.viewholder.StudyCategoryAdapterListener
import com.iron.espresso.utils.ToolbarHelper

class StudyCategoryActivity : AppCompatActivity(), StudyCategoryAdapterListener {

    private lateinit var binding: ActivityCategoryStudyBinding

    private lateinit var toolbarHelper: ToolbarHelper

    private val categoryAdapter by lazy { CategoryAdapter() }

    override fun getData(item: StudyCategoryItem) {
        startActivity(StudyCreateActivity.getInstance(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_category_study)
        binding.lifecycleOwner = this

        toolbarHelper = ToolbarHelper(this, binding.appbar).apply {
            setTitle(TITLE)
            setNavigationIcon(R.drawable.ic_back_24)
        }

        binding.apply {
            viewStudyCategory.adapter = categoryAdapter
            viewStudyCategory.layoutManager = GridLayoutManager(this@StudyCategoryActivity, 2)
            categoryAdapter.addAll(StudyFragment.DUMMY_DATA)
        }
        categoryAdapter.setItemClickListener(this)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return true
    }

    companion object {

        private const val TITLE = "스터디 만들기"

        fun getInstance(context: Context) =
            Intent(context, StudyCategoryActivity::class.java)
    }


}