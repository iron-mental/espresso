package com.iron.espresso.presentation.home.study

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.iron.espresso.R
import com.iron.espresso.databinding.ActivityCreateStudyBinding
import com.iron.espresso.ext.load
import com.iron.espresso.utils.ToolbarHelper

class StudyCreateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateStudyBinding

    private lateinit var toolbarHelper: ToolbarHelper


    private val image by lazy {
        intent.getIntExtra(KEY, 0)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_study)
        binding.lifecycleOwner = this

        toolbarHelper = ToolbarHelper(this, binding.appbar).apply {
            setTitle(TITLE)
            setNavigationIcon(R.drawable.ic_back_24)
        }

        binding.placeContainer.setOnClickListener {
            startActivity()
        }

        binding.image.transitionName = image.toString()
        binding.image.load(image, true) {
            supportPostponeEnterTransition()
        }

        ObjectAnimator.ofFloat(binding.title, View.ALPHA, 0.2f, 1.0f).apply {
            duration = 1500
            start()
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

        private const val KEY = "key"

        fun getInstance(context: Context, item: Int) =
            Intent(context, StudyCreateActivity::class.java).apply {
                putExtra(KEY, item)
            }

    }


}