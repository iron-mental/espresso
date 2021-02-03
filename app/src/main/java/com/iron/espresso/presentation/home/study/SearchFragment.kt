package com.iron.espresso.presentation.home.study

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.google.android.material.chip.Chip
import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment :
    BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private val viewModel by viewModels<SearchStudyViewModel>()
    private lateinit var hotKeywordButton: Chip
    private lateinit var searchEditText: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchEditText = EditText(context).apply {
            hint = context.getString(R.string.search_hint)
            setSingleLine()
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            requestFocus()
            imeOptions = EditorInfo.IME_ACTION_SEARCH
            setOnEditorActionListener { _, actionId, _ ->
                if (text.isNotEmpty() && actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //키보드 내리기
                    val inputMethodManager =
                        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)

                    keywordSearch(text.toString())
                    true
                } else {
                    false
                }

            }
        }

        baseActivity?.setCustomView(searchEditText)

        binding.placeSearchButton.setOnClickListener {
            Toast.makeText(context, binding.placeSearchButton.text, Toast.LENGTH_SHORT).show()
        }

        viewModel.getHotKeywordList()

        viewModel.hotKeywordList.observe(viewLifecycleOwner, { hotKeywordList ->
            // 핫 키워드 버튼 클릭 시 검색 창 text 대응
            hotKeywordList.forEach { keyWord ->
                hotKeywordButton = Chip(context).apply {
                    text = keyWord.title
                    setOnClickListener {
                        keywordSearch(text.toString())
                    }
                }
                binding.hotKeywordGroup.addView(hotKeywordButton)
            }
        })
    }

    private fun keywordSearch(text: String) {
        parentFragmentManager.beginTransaction()
            .replace(
                R.id.search_frg_container,
                SearchResultFragment.newInstance(text)
            )
            .addToBackStack(null)
            .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
            }
        }
        return true
    }

    companion object {
        fun newInstance() =
            SearchFragment()
    }
}