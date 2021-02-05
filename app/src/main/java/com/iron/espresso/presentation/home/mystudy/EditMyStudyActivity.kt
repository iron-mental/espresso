package com.iron.espresso.presentation.home.mystudy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.data.model.MyStudyViewType
import com.iron.espresso.databinding.ActivityEditMyStudyBinding
import com.iron.espresso.ext.toast
import com.iron.espresso.model.response.study.MyStudyResponse
import com.iron.espresso.presentation.home.mystudy.adapter.MyStudyAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditMyStudyActivity :
    BaseActivity<ActivityEditMyStudyBinding>(R.layout.activity_edit_my_study) {

    private val myStudyViewModel by viewModels<MyStudyViewModel>()
    private val myStudyAdapter by lazy { MyStudyAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setToolbarTitle(resources.getString(R.string.edit_study))
        setNavigationIcon(R.drawable.ic_back_24)

        binding.run {
            rvMyStudy.adapter = myStudyAdapter
            myStudyViewModel.showMyStudyList()
        }

        myStudyViewModel.studyList.observe(this, { studyList ->
            myStudyAdapter.setMyStudyView(MyStudyViewType.EDIT)
            myStudyAdapter.replaceAll(studyList)
        })

        myStudyAdapter.setItemClickListener(object : MyStudyAdapter.ItemClickListener {
            override fun onClick(item: MyStudyResponse) {
                toast("${item.title}")
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_edit_mystudy, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
            R.id.leave -> {
                toast("나가기 테스트")
            }
        }
        return true
    }

    companion object {
        fun getIntent(context: Context) =
            Intent(context, EditMyStudyActivity::class.java)
    }
}