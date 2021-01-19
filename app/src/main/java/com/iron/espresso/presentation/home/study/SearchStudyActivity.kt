package com.iron.espresso.presentation.home.study

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.iron.espresso.Logger
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.databinding.ActivitySearchStudyBinding
import com.iron.espresso.presentation.home.mystudy.MyStudyDetailActivity
import com.iron.espresso.presentation.home.study.adapter.StudyListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchStudyActivity :
    BaseActivity<ActivitySearchStudyBinding>(R.layout.activity_search_study) {

    private val viewModel by viewModels<SearchStudyViewModel>()
    private val studyListAdapter = StudyListAdapter()
    private lateinit var searchEditText: EditText
    private lateinit var hotKeywordButton: Chip

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        searchEditText = EditText(this).apply {
            hint = context.getString(R.string.search_hint)
            setSingleLine()
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            requestFocus()
            imeOptions = EditorInfo.IME_ACTION_SEARCH
            setOnEditorActionListener { _, _, _ ->
                var handled = false     //키보드 내림
                if (text.isNotEmpty()) {
                    Logger.d("성공")
                    viewModel.showSearchStudyList("$text")
                    binding.searchContainer.visibility = View.GONE
                    binding.studyList.visibility = View.VISIBLE
                } else {
                    Logger.d("실패")
                    handled = true      //키보드 유지
                }
                handled
            }
        }

        setCustomView(searchEditText)
        setNavigationIcon(R.drawable.ic_back_24)

        binding.studyList.adapter = studyListAdapter

        binding.placeSearchButton.setOnClickListener {
            Toast.makeText(this, binding.placeSearchButton.text, Toast.LENGTH_SHORT).show()
        }

        binding.swipeRefresh.apply {
            setOnRefreshListener {
                viewModel.showSearchStudyList("${searchEditText.text}")

                this.isRefreshing = false
            }
        }


        viewModel.getHotKeywordList()

        viewModel.studyList.observe(this, Observer { studyList ->
            studyListAdapter.setItemList(studyList)
        })

        viewModel.scrollItem.observe(this, Observer {
            studyListAdapter.setScrollItem(it)
        })

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

        studyListAdapter.setItemClickListener { studyItem ->
            if (studyItem.isMember) {
                startActivity(
                    MyStudyDetailActivity.getInstance(
                        this,
                        studyItem.title,
                        studyItem.id
                    )
                )
            } else {
                startActivity(StudyDetailActivity.getInstance(this, studyItem.id))
            }
        }

        scrollListener()
    }

    private fun scrollListener() {
        binding.studyList.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val linear =
                        binding.studyList.layoutManager as LinearLayoutManager

                    if (linear.findLastCompletelyVisibleItemPosition()
                        == studyListAdapter.itemCount - 1
                    ) {
                        if (studyListAdapter.itemCount >= 10) {
                            viewModel.getSearchStudyListPaging()
                        }
                    }
                }
            }
        )
    }

    override fun onBackPressed() {
        if (binding.searchContainer.visibility == View.VISIBLE) {
            super.onBackPressed()
        } else {
            binding.searchContainer.visibility = View.VISIBLE
            binding.studyList.visibility = View.GONE
            searchEditText.setText("")
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
            Intent(context, SearchStudyActivity::class.java)
    }
}