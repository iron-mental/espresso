package com.wswon.picker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.wswon.picker.common.BaseActivity
import com.wswon.picker.databinding.ActivityImageDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageDetailActivity :
    BaseActivity<ActivityImageDetailBinding>(R.layout.activity_image_detail) {

    private val imageDetailAdapter by lazy { ImageDetailAdapter() }

    private val viewModel: ImageDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initViewModel()
    }

    private fun initView() {
        val currentDirectory = intent.getStringExtra(ARG_CURRENT_DIRECTORY).orEmpty()

        binding.run {
            vm = viewModel
            vpImageDetail.adapter = imageDetailAdapter
            viewModel.getImageUri(currentDirectory)
        }
    }

    private fun initViewModel() {
        viewModel.run {
            imageDetailItemList.observe(this@ImageDetailActivity, Observer { imageItem ->
                imageDetailAdapter.replaceAll(imageItem)
                val position = intent.getIntExtra(ARG_CLICKED_POSITION, 0)
                moveAdapterPosition(position)
            })
        }
    }

    private fun moveAdapterPosition(position: Int) {
        binding.vpImageDetail.setCurrentItem(position, false)
    }

    companion object {
        fun getIntent(context: Context, directory: String, position: Int) =
            Intent(context, ImageDetailActivity::class.java).apply {
                putExtra(ARG_CURRENT_DIRECTORY, directory)
                putExtra(ARG_CLICKED_POSITION, position)
            }


        private const val ARG_CURRENT_DIRECTORY = "ARG_CURRENT_DIRECTORY"
        private const val ARG_CLICKED_POSITION = "ARG_CLICKED_POSITION"
    }
}