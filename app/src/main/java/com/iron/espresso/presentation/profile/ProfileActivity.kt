package com.iron.espresso.presentation.profile

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.iron.espresso.Logger
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.base.MenuSet
import com.iron.espresso.databinding.ActivityProfileBinding
import com.iron.espresso.di.ApiModule
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.model.api.RegisterStudyRequest
import com.iron.espresso.presentation.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.view_profile_header.view.*
import retrofit2.HttpException


@AndroidEntryPoint
class ProfileActivity :
    BaseActivity<ActivityProfileBinding>(R.layout.activity_profile) {

    private val viewModel by viewModels<ProfileViewModel>()

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel

        ApiModule.provideStudyApi()
            .registerStudy(
                "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwiZW1haWwiOiJya2RjamYwMTIyQG5hdmVyLmNvbSIsIm5pY2tuYW1lIjoi7J207ZWY7J20IiwiaWF0IjoxNjA2NjU0MjczLCJleHAiOjEwNjA2NjU0MjczLCJpc3MiOiJ0ZXJtaW5hbC1zZXJ2ZXIiLCJzdWIiOiJ1c2VySW5mby1hY2Nlc3MifQ.AMBmWRyV3awRri4pE3KSOUbIM929kMdw92WGqyaOkFI",
                RegisterStudyRequest(
                    "android", "공부허자", "실제거리가", "211", "2121", 0.0, 0.0, "2112", "212", "2112"
                ).toMultipartBody()
            )
            .networkSchedulers()
            .subscribe({
                Logger.d("$it")
            }, {
                val error = it as HttpException
                val errorbody = error.response()?.errorBody()?.string()
                Logger.d("${error.code()}")
                Logger.d("$errorbody")
            })

        setTitle(R.string.profile_title)
        setNavigationIcon(R.drawable.ic_back_24)

        viewModel.avatarUrl.observe(this, Observer { avatarUrl ->
            Glide.with(this)
                .load(avatarUrl)
                .into(binding.layoutHeader.profile_image)
        })
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (menu != null) {
            setMenuItems(menu)
        }

        return super.onPrepareOptionsMenu(menu)
    }

    private fun setMenuItems(menu: Menu) {
        val groupId = 0
        val set = MenuSet.ICON_SHARE

        if (menu.findItem(set.menuId) == null) {
            menu.add(groupId, set.menuId, 0, set.titleResId)
                .setIcon(ContextCompat.getDrawable(this, set.imageResId))
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.actions, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
            R.id.menu_item_share -> {
                Toast.makeText(this, "공유하기 클릭", Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(this, "${item.title}", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }

    companion object {
        fun getInstance(context: Context) =
            Intent(context, ProfileActivity::class.java)
    }
}