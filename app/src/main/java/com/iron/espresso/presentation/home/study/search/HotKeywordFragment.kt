package com.iron.espresso.presentation.home.study.search

import android.content.Context
import android.os.Bundle
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
import com.iron.espresso.databinding.FragmentHotKeywordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HotKeywordFragment :
    BaseFragment<FragmentHotKeywordBinding>(R.layout.fragment_hot_keyword) {

    private val viewModel by viewModels<HotKeywordViewModel>()
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

                    showResultView(text.toString())
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

        viewModel.run {
            getHotKeywordList()

            hotKeywordList.observe(viewLifecycleOwner, { hotKeywordList ->
                // 핫 키워드 버튼 클릭 시 검색 창 text 대응
                hotKeywordList.forEach { keyWord ->
                    hotKeywordButton = Chip(context).apply {
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

    // 2. 화면이 검색할때마다 쌓인다.

    private fun showResultView(text: String) {
        parentFragmentManager.beginTransaction().hide(this).commit()
        parentFragmentManager.beginTransaction()
            .add(
                R.id.search_frg_container,
                SearchResultFragment.newInstance(text)
            )
            .commit()
    }

    companion object {
        fun newInstance() =
            HotKeywordFragment()
    }
}