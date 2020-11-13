package com.iron.espresso.presentation.place

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.UiThread
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.iron.espresso.R
import com.iron.espresso.databinding.ActivitySearchPlaceDetailBinding
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback

class SearchPlaceDetailActivity : FragmentActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivitySearchPlaceDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_place_detail)

        binding.selectButton.setOnClickListener {
            setResult(RESULT_OK)
            finish()

            val fm = supportFragmentManager
            val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
                ?: MapFragment.newInstance().also {
                    fm.beginTransaction().add(R.id.map, it).commit()
                }

            mapFragment.getMapAsync(this)
        }
    }

    @UiThread
    override fun onMapReady(naverMap: NaverMap) {

    }

    companion object {
        fun getInstance(context: Context) =
            Intent(context, SearchPlaceDetailActivity::class.java)
    }
}