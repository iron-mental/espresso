package com.iron.espresso.presentation.home.study.search

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.google.android.material.chip.Chip
import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentHotKeywordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HotKeywordFragment :
    BaseFragment<FragmentHotKeywordBinding>(R.layout.fragment_hot_keyword) {

    private val viewModel by viewModels<HotKeywordViewModel>()
    private lateinit var searchEditText: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchView = layoutInflater.inflate(R.layout.view_search, null)
        val clearButton = searchView.findViewById<ImageView>(R.id.clear_button)

        baseActivity?.setCustomView(searchView)

        searchEditText = searchView.findViewById<EditText>(R.id.edit_view).apply {
            setOnEditorActionListener { _, actionId, _ ->
                if (text.isNotEmpty() && actionId == EditorInfo.IME_ACTION_SEARCH) {
                    showResultView(text.toString())
                    true
                } else {
                    false
                }
            }
            doOnTextChanged { text, _, _, _ ->
                clearButton.isVisible = !text.isNullOrEmpty()
            }
        }
        clearButton.setOnClickListener {
            resetSearchView()
        }

        binding.placeSearchButton.setOnClickListener {
            Toast.makeText(context, binding.placeSearchButton.text, Toast.LENGTH_SHORT).show()
        }

        viewModel.run {
            getHotKeywordList()

            hotKeywordList.observe(viewLifecycleOwner, { hotKeywordList ->
                // 핫 키워드 버튼 클릭 시 검색 창 text 대응
                hotKeywordList.forEach { keyWord ->
                    val hotKeywordButton = Chip(context).apply {
                        text = keyWord.word
                        setOnClickListener {
                            showResultView(text.toString())
                        }
                    }
                    binding.hotKeywordGroup.addView(hotKeywordButton)
                }
            })
        }
    }
    private fun resetSearchView() {
        searchEditText.text.clear()
        searchEditText.requestFocus()

        val inputMethodManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(searchEditText, 0)
    }

    private fun setSearchView(keyword: String) {
        searchEditText.apply {
            setText(keyword)
            isFocusable = false
            isFocusableInTouchMode = true
        }

        val inputMethodManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun showResultView(keyword: String) {
        setSearchView(keyword)

        val fragment = parentFragmentManager.findFragmentByTag(RESULT_TAG)
        if (fragment != null) {
            parentFragmentManager.beginTransaction().remove(fragment).commit()
        }
        parentFragmentManager.beginTransaction()
            .hide(this)
            .add(
                R.id.search_frg_container,
                SearchResultFragment.newInstance(keyword),
                RESULT_TAG
            )
            .commit()
    }

    companion object {
        private const val RESULT_TAG = "result"
        fun newInstance() =
            HotKeywordFragment()
    }
}