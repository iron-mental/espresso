package com.iron.espresso.presentation.place

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.databinding.ActivitySearchPlaceBinding
import com.iron.espresso.model.response.Place
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchPlaceActivity :
    BaseActivity<ActivitySearchPlaceBinding>(R.layout.activity_search_place) {

    private val viewModel by viewModels<SearchPlaceViewModel>()

    private lateinit var searchEditText: EditText
    private val placeAdapter = PlaceAdapter()
    private lateinit var placeList: List<Place>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        searchEditText = EditText(this).apply {
            hint = TOOLBAR_HINT
            setSingleLine()
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            requestFocus()
            imeOptions = EditorInfo.IME_ACTION_SEARCH
            setOnEditorActionListener { search, _, _ ->
                var handled = false     //키보드 내림
                if (search.text.isNotEmpty()) {
                    Log.d("TAG", "성공")
                    viewModel.searchPlace(text.toString())
                } else {
                    Log.d("TAG", "실패")
                    handled = true      //키보드 유지
                }
                handled
            }
        }

        setToolbarTitle(null)
        setNavigationIcon(R.drawable.ic_back_24)
        setCustomView(searchEditText)

        binding.placeList.run {
            adapter = placeAdapter
            addItemDecoration(
                DividerItemDecoration(this@SearchPlaceActivity, LinearLayout.VERTICAL)
            )
        }

        viewModel.placeList.observe(this, { place ->
            placeList = place
            placeAdapter.run {
                setItemList(placeList)
            }
        })
        placeAdapter.setItemClickListener { item ->
            Log.d("ITEMS", item.toString())
            startActivityForResult(
                SearchPlaceDetailActivity.getInstance(this@SearchPlaceActivity, item),
                REQ_CODE
            )
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQ_CODE) {
            if (resultCode == RESULT_OK) {
                setResult(RESULT_OK, data)
                finish()
            }
        }
    }

    companion object {
        fun getInstance(context: Context) =
            Intent(context, SearchPlaceActivity::class.java)

        const val TOOLBAR_HINT = "장소를 입력하세요"
        const val REST_API_KEY = "KakaoAK 58071fbe087f96f72e3baf3fb28f2f6a"
        const val REQ_CODE = 1

    }
}