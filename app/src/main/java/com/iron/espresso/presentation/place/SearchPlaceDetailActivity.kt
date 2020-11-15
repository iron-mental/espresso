package com.iron.espresso.presentation.place

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.UiThread
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.iron.espresso.R
import com.iron.espresso.databinding.ActivitySearchPlaceDetailBinding
import com.iron.espresso.model.response.Place
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
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
        }

        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map, it).commit()
            }

        mapFragment.getMapAsync(this)
    }

    @UiThread
    override fun onMapReady(naverMap: NaverMap) {

        val placeItems = intent.getSerializableExtra(PLACE_ITEMS) as Place
        naverMap.cameraPosition = CameraPosition(LatLng(placeItems.y, placeItems.x), 16.0)
        binding.address.text = placeItems.addressName

    }

    companion object {
        private const val PLACE_ITEMS = "place_items"

        fun getInstance(context: Context, item: Place) =
            Intent(context, SearchPlaceDetailActivity::class.java).apply {
                putExtra(PLACE_ITEMS, item)
            }
    }
}