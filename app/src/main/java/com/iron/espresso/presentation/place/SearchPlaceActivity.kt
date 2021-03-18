package com.iron.espresso.presentation.place

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
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

        val searchView = layoutInflater.inflate(R.layout.view_search, null)
        val clearButton = searchView.findViewById<ImageView>(R.id.clear_button)

        setToolbarTitle(null)
        setNavigationIcon(R.drawable.ic_back_24)
        setCustomView(searchView)

        searchEditText = searchView.findViewById<EditText>(R.id.edit_view).apply {
            hint = TOOLBAR_HINT
            setOnEditorActionListener { _, actionId, _ ->
                if (text.isNotEmpty() && actionId == EditorInfo.IME_ACTION_SEARCH) {
                    viewModel.searchPlace(text.toString())
                    true
                } else {
                    false
                }
            }
            doOnTextChanged { text, _, _, _ ->
                clearButton.isVisible = !text.isNullOrEmpty()
            }
        }

        resetSearchView()

        clearButton.setOnClickListener {
            resetSearchView()
        }

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

    private fun resetSearchView() {
        searchEditText.text.clear()
        searchEditText.requestFocus()

        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(searchEditText, 0)
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