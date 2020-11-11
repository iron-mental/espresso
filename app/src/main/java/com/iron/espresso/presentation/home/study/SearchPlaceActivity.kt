package com.iron.espresso.presentation.home.study

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import com.iron.espresso.R
import com.iron.espresso.ToolbarHelper
import com.iron.espresso.data.model.PlaceItem
import com.iron.espresso.databinding.ActivitySearchPlaceBinding
import com.iron.espresso.presentation.home.study.adapter.PlaceAdapter

class SearchPlaceActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchPlaceBinding
    private lateinit var toolbarHelper: ToolbarHelper
    private lateinit var searchEditText: EditText
    private val placeAdapter = PlaceAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_place)

        searchEditText = EditText(this)
        toolbarHelper = ToolbarHelper(this, binding.appbar.apply {
        }).apply {
            setTitle(null)
            setNavigationIcon(R.drawable.ic_back_24)
            setCustomView(searchEditText.apply {
                hint = TOOLBAR_HINT
                setSingleLine()
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            })
        }

        val placeItemList = mutableListOf<PlaceItem>().apply {
            add(PlaceItem("넥슨코리아", "게임제작", "경기 성남시 분당구 판교로 256번길 25"))
            add(PlaceItem("넥슨코리아", "게임제작", "경기 성남시 분당구 판교로 256번길 25"))
            add(PlaceItem("넥슨코리아", "게임제작", "경기 성남시 분당구 판교로 256번길 25"))
            add(PlaceItem("넥슨코리아", "게임제작", "경기 성남시 분당구 판교로 256번길 25"))
        }

        placeAdapter.run {
            setItemList(placeItemList)
            setItemClickListener { title ->
                Toast.makeText(this@SearchPlaceActivity, title, Toast.LENGTH_SHORT).show()
            }
        }

        binding.placeList.run {
            adapter = placeAdapter
            addItemDecoration(
                DividerItemDecoration(
                    this@SearchPlaceActivity,
                    LinearLayout.VERTICAL
                )
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

    companion object {
        fun getInstance(context: Context) =
            Intent(context, SearchPlaceActivity::class.java)

        const val TOOLBAR_HINT = "넥슨"
    }
}