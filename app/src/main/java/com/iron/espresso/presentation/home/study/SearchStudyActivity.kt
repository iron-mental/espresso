package com.iron.espresso.presentation.home.study

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.iron.espresso.Logger
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.databinding.ActivitySearchStudyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchStudyActivity :
    BaseActivity<ActivitySearchStudyBinding>(R.layout.activity_search_study) {

    private lateinit var searchEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setNavigationIcon(R.drawable.ic_back_24)

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
                    fragmentManager.beginTransaction()
                        .replace(
                            R.id.search_frg_container,
                            SearchResultFragment.newInstance(text.toString())
                        )
                        .commit()
                } else {
                    Logger.d("실패")
                    handled = true      //키보드 유지
                }
                handled
            }
        }

        setCustomView(searchEditText)

        supportFragmentManager.beginTransaction()
            .replace(
                R.id.search_frg_container,
                SearchFragment.newInstance()
            )
            .commit()
    }

    private fun search(text: String) {
        supportFragmentManager.beginTransaction()
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