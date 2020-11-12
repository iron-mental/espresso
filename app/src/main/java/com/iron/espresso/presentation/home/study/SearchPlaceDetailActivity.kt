package com.iron.espresso.presentation.home.study

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.iron.espresso.R
import com.iron.espresso.databinding.ActivitySearchPlaceDetailBinding

class SearchPlaceDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchPlaceDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_place_detail)
    }
}