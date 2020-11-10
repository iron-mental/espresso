package com.iron.espresso.presentation.home.study

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.marginEnd
import androidx.databinding.DataBindingUtil
import com.iron.espresso.MenuSet
import com.iron.espresso.R
import com.iron.espresso.ToolbarHelper
import com.iron.espresso.databinding.ActivitySearchPlaceBinding

class SearchPlaceActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchPlaceBinding
    private lateinit var toolbarHelper: ToolbarHelper
    private lateinit var searchEditText: EditText

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