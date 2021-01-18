package com.iron.espresso.presentation.home.study

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.chip.Chip
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.databinding.ActivitySearchStudyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchStudyActivity :
    BaseActivity<ActivitySearchStudyBinding>(R.layout.activity_search_study) {

    private val viewModel by viewModels<SearchStudyViewModel>()
    private lateinit var searchEditText: EditText
    private lateinit var hotKeywordButton: Chip

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        searchEditText = EditText(this).apply {
            hint = context.getString(R.string.search_hint)
            setSingleLine()
        }

        setCustomView(searchEditText)
        setNavigationIcon(R.drawable.ic_back_24)

        binding.placeSearchButton.setOnClickListener {
            Toast.makeText(this, binding.placeSearchButton.text, Toast.LENGTH_SHORT).show()
        }

        viewModel.getHotKeywordList()
        viewModel.showSearchStudyList("강남구")

        viewModel.hotKeywordList.observe(this, Observer { hotKeywordList ->
            // 핫 키워드 버튼 클릭 시 검색 창 text 대응
            hotKeywordList.forEach { keyWord ->
                hotKeywordButton = Chip(this).apply {
                    text = keyWord.title
                    setOnClickListener {
                        Toast.makeText(this@SearchStudyActivity, text, Toast.LENGTH_SHORT).show()
                        searchEditText.setText(text)
                    }
                }
                binding.hotKeywordGroup.addView(hotKeywordButton)
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
            Intent(context, SearchStudyActivity::class.java)
    }
}