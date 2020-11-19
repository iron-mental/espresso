package com.iron.espresso.presentation.place

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.annotation.UiThread
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.iron.espresso.R
import com.iron.espresso.data.model.PlaceItem
import com.iron.espresso.databinding.ActivitySearchPlaceDetailBinding
import com.iron.espresso.model.api.KakaoApi
import com.iron.espresso.model.response.*
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate.REASON_GESTURE
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchPlaceDetailActivity : FragmentActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivitySearchPlaceDetailBinding

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

        var firstEvent = true
        var firstAnim = false
        val placeItems = intent.getSerializableExtra(PLACE_ITEMS) as Place

        //초기 카메라 위치 설정
        naverMap.cameraPosition = CameraPosition(LatLng(placeItems.lat, placeItems.lng), 16.0)

        //카메라 이동할 때
        naverMap.addOnCameraChangeListener { reason, _ ->
            if (reason == REASON_GESTURE) {
                if (firstAnim) { //처음 한번만 실행
                    firstAnim = false
                    ObjectAnimator.ofFloat(binding.marker, "translationY", -25f).apply {
                        binding.marker.drawable.alpha = 50
                        duration = 100
                        start()
                    }

                }
            }
        }

        //카메라 이동 멈췄을 때
        naverMap.addOnCameraIdleListener {
            if (firstEvent) {     // 처음 한번만 실행
                searchCoord(
                    naverMap.cameraPosition.target.longitude,
                    naverMap.cameraPosition.target.latitude,
                    placeItems.placeName
                )
                firstEvent = false
            } else {
                searchCoord(
                    naverMap.cameraPosition.target.longitude,
                    naverMap.cameraPosition.target.latitude,
                    null
                )
                ObjectAnimator.ofFloat(binding.marker, "translationY", 0f).apply {
                    binding.marker.drawable.alpha = 255
                    duration = 100
                    start()
                }
            }
            firstAnim = true
        }
    }

    private fun searchCoord(lat: Double, lng: Double, placeName: String?) {

        //retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(SearchPlaceActivity.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(KakaoApi::class.java)
        val callGetLocal = service.convertAddressToCoord(SearchPlaceActivity.REST_API_KEY, lat, lng)

        callGetLocal.enqueue(object : Callback<LocalResponse> {
            override fun onResponse(
                call: Call<LocalResponse>,
                response: Response<LocalResponse>
            ) {
                Log.d("TAG", "response : ${response.body()?.documents}")
                Log.d("TAG", "성공 : ${response.raw()}")

                val roadAddress = response.body()?.documents?.get(0)?.roadAddress
                val address = response.body()?.documents?.get(0)?.address

                if (roadAddress?.addressName != null) {
                    binding.address.text = roadAddress.addressName
                } else {
                    binding.address.text = address?.addressName
                }
                val realAddress = address ?: return

                binding.selectButton.setOnClickListener {
                    val placeItem = PlaceItem(
                        lat,
                        lng,
                        realAddress.region1depthName,
                        realAddress.region2depthName,
                        binding.address.text.toString(),
                        placeName,
                        binding.addressDetail.text.toString()
                    )

                    val placeItems = intent.putExtra("placeItems", placeItem)

                    setResult(RESULT_OK, placeItems)
                    finish()
                }

            }

            override fun onFailure(call: Call<LocalResponse>, t: Throwable) {
                Log.d("TAG", "실패 : $t")
            }
        })
    }

    companion object {
        private const val PLACE_ITEMS = "place_items"

        fun getInstance(context: Context, item: Place) =
            Intent(context, SearchPlaceDetailActivity::class.java).apply {
                putExtra(PLACE_ITEMS, item)
            }
    }
}