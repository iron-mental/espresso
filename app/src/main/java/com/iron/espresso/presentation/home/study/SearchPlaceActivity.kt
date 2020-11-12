package com.iron.espresso.presentation.home.study

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import com.iron.espresso.R
import com.iron.espresso.ToolbarHelper
import com.iron.espresso.databinding.ActivitySearchPlaceBinding
import com.iron.espresso.model.api.KakaoApi
import com.iron.espresso.model.response.Place
import com.iron.espresso.model.response.PlaceResponse
import com.iron.espresso.presentation.home.study.adapter.PlaceAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchPlaceActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchPlaceBinding
    private lateinit var toolbarHelper: ToolbarHelper
    private lateinit var searchEditText: EditText
    private val placeAdapter = PlaceAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_place)

        searchEditText = EditText(this)
        toolbarHelper = ToolbarHelper(this, binding.appbar).apply {
            setTitle(null)
            setNavigationIcon(R.drawable.ic_back_24)
            setCustomView(searchEditText.apply {
                hint = TOOLBAR_HINT
                setSingleLine()
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                requestFocus()
                imeOptions = EditorInfo.IME_ACTION_SEARCH
                setOnEditorActionListener { search, action, event ->
                    var handled = false     //키보드 내림
                    if (search.text.isNotEmpty()) {
                        searchPlace(text.toString())
                        Log.d("TAG", "성공")
                    } else {
                        Log.d("TAG", "실패")
                        handled = true      //키보드 유지
                    }
                    handled
                }
            })
        }

        binding.placeList.run {
            adapter = placeAdapter
            addItemDecoration(
                DividerItemDecoration(this@SearchPlaceActivity, LinearLayout.VERTICAL)
            )
        }
    }

    private fun searchPlace(keyword: String) {

        //retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(KakaoApi::class.java)
        val callGetTest = service.getPlacesByKeyword(REST_API_KEY, keyword)

        callGetTest.enqueue(object : Callback<PlaceResponse> {
            override fun onResponse(
                call: Call<PlaceResponse>,
                response: Response<PlaceResponse>
            ) {
                Log.d("TAG", "response : ${response.body()}")
                Log.d("TAG", "성공 : ${response.raw()}")

                val placeList = mutableListOf<Place>().apply {
                    response.body()?.documents?.let { addAll(it) }
                }
                placeAdapter.run {
                    setItemList(placeList)
                    setItemClickListener { title ->
                        Toast.makeText(this@SearchPlaceActivity, title, Toast.LENGTH_SHORT).show()
                    }
                }

            }

            override fun onFailure(call: Call<PlaceResponse>, t: Throwable) {
                Log.d("TAG", "실패 : $t")
            }
        })


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
        fun getInstance(context: Context) =
            Intent(context, SearchPlaceActivity::class.java)

        const val TOOLBAR_HINT = "장소를 입력하세요"
        const val REST_API_KEY = "KakaoAK 58071fbe087f96f72e3baf3fb28f2f6a"
        const val BASE_URL = "https://dapi.kakao.com/"
    }
}