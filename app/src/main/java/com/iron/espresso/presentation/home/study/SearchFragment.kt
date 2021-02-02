package com.iron.espresso.presentation.home.study

import android.os.Bundle
import android.view.MenuItem
import android.view.View
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                        hotKeywordClick(text.toString())
                    }
                }
                binding.hotKeywordGroup.addView(hotKeywordButton)
            }
        })
    }
    private fun hotKeywordClick(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        parentFragmentManager.beginTransaction()
            .replace(
                R.id.search_frg_container,
                SearchResultFragment.newInstance(text)
            )
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