package com.iron.espresso.presentation.place

import android.content.Context
import android.content.Intent
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

        binding.selectButton.setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }
    }

    companion object {
        fun getInstance(context: Context) =
            Intent(context, SearchPlaceDetailActivity::class.java)
    }
}