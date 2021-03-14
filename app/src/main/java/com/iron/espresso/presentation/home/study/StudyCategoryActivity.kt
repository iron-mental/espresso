package com.iron.espresso.presentation.home.study

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.databinding.ActivityCategoryStudyBinding
import com.iron.espresso.presentation.home.study.adapter.CategoryAdapter
import com.iron.espresso.presentation.home.study.adapter.viewholder.StudyCategoryAdapterListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StudyCategoryActivity :
    BaseActivity<ActivityCategoryStudyBinding>(R.layout.activity_category_study),
    StudyCategoryAdapterListener {

    private val categoryAdapter by lazy { CategoryAdapter() }
    private val viewModel by viewModels<StudyViewModel>()

    override fun getData(item: String, imageView: ImageView) {
        startActivity(StudyCreateActivity.getIntent(this, item))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setToolbarTitle(TITLE)
        setNavigationIcon(R.drawable.ic_back_24)

        binding.apply {
            viewStudyCategory.adapter = categoryAdapter
            viewStudyCategory.layoutManager = GridLayoutManager(this@StudyCategoryActivity, 2)
        }
        categoryAdapter.setItemClickListener(this)

        viewModel.run {
            getStudyCategory()
            categoryList.observe(this@StudyCategoryActivity, { categoryList ->
                categoryAdapter.addAll(categoryList)
            })
        }
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

        fun getIntent(context: Context) =
            Intent(context, StudyCategoryActivity::class.java)
    }
}