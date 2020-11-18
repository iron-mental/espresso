package com.iron.espresso.presentation.home.study

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.databinding.ActivityCategoryStudyBinding
import com.iron.espresso.presentation.StudyCategoryItem
import com.iron.espresso.presentation.home.study.adapter.CategoryAdapter
import com.iron.espresso.presentation.home.study.adapter.viewholder.StudyCategoryAdapterListener

class StudyCategoryActivity :
    BaseActivity<ActivityCategoryStudyBinding>(R.layout.activity_category_study),
    StudyCategoryAdapterListener {

    private val categoryAdapter by lazy { CategoryAdapter() }

    override fun getData(item: StudyCategoryItem, imageView: ImageView) {
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            imageView,
            imageView.transitionName
        ).toBundle()
        startActivity(StudyCreateActivity.getInstance(this, item.image), options)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_category_study)
        binding.lifecycleOwner = this

        setToolbarTitle(TITLE)
        setNavigationIcon(R.drawable.ic_back_24)

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