package com.iron.espresso.presentation.place

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.UiThread
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.iron.espresso.R
import com.iron.espresso.data.model.LocalItem
import com.iron.espresso.databinding.ActivitySearchPlaceDetailBinding
import com.iron.espresso.model.response.*
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate.REASON_GESTURE
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchPlaceDetailActivity : FragmentActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivitySearchPlaceDetailBinding
    private val viewModel = viewModels<SearchPlaceDetailViewModel>()
    private lateinit var localItem: LocalItem


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_place_detail)

        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map, it).commit()
            }

        mapFragment.getMapAsync(this)
    }

    @UiThread
    override fun onMapReady(naverMap: NaverMap) {

        var gesturing = false
        val placeItems = intent.getSerializableExtra(PLACE_ITEM) as Place
        var placeName = placeItems.placeName

        //초기 카메라 위치 설정
        naverMap.cameraPosition = CameraPosition(LatLng(placeItems.lat, placeItems.lng), 16.0)

        //카메라 이동할 때
        naverMap.addOnCameraChangeListener { reason, _ ->
            if (reason == REASON_GESTURE) {
                if (gesturing) { // 로직 한번만 실
                    gesturing = false
                    ObjectAnimator.ofFloat(binding.marker, "translationY", -25f).apply {
                        binding.marker.drawable.alpha = 50
                        duration = 100
                        start()
                    }
                    placeName = ""
                }
            }
        }

        //카메라 이동 멈췄을 때
        naverMap.addOnCameraIdleListener {
            if (placeName.isNotEmpty()) {
            } else {
                ObjectAnimator.ofFloat(binding.marker, "translationY", 0f).apply {
                    binding.marker.drawable.alpha = 255
                    duration = 100
                    start()
                }
            }

            viewModel.value.searchCoord(
                naverMap.cameraPosition.target.longitude,
                naverMap.cameraPosition.target.latitude,
                placeName
            )

            gesturing = true
        }

        viewModel.value.sendLocalItem.observe(this) { localItem ->
            this.localItem = localItem
            binding.address.text = localItem.addressName
        }

        binding.selectButton.setOnClickListener {
            localItem.locationDetail = binding.addressDetail.text.toString()
            val intent = Intent().putExtra(LOCAL_ITEM, localItem)

            setResult(RESULT_OK, intent)
            finish()
        }
    }


    companion object {
        private const val PLACE_ITEM = "place_items"
        const val LOCAL_ITEM = "local_items"

        fun getInstance(context: Context, item: Place) =
            Intent(context, SearchPlaceDetailActivity::class.java).apply {
                putExtra(PLACE_ITEM, item)
            }
    }
}