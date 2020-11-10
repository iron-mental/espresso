package com.iron.espresso.presentation.home.study

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.iron.espresso.R
import com.iron.espresso.databinding.ActivitySearchPlaceBinding

class SearchPlaceActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchPlaceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_place)
    }
}