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
import com.iron.espresso.R
import com.iron.espresso.UserHolder
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.base.MenuSet
import com.iron.espresso.databinding.ActivityProfileBinding
import com.iron.espresso.presentation.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileActivity :
    BaseActivity<ActivityProfileBinding>(R.layout.activity_profile) {

    private val viewModel by viewModels<ProfileViewModel>()

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel

        UserHolder.get()?.let {
            viewModel.setProfile(user = it)
        }

        setToolbarTitle(R.string.profile_title)
        setNavigationIcon(R.drawable.ic_back_24)

        viewModel.avatarUrl.observe(this, Observer { avatarUrl ->
            Glide.with(this)
                .load(avatarUrl)
                .into(binding.layoutHeader.profileImage)
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
        inflater.inflate(R.menu.menu_profile, menu)
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
            R.id.edit_profile -> {
                Toast.makeText(this, "${item.title}", Toast.LENGTH_SHORT).show()
            }
            else -> {

            }
        }
        return true
    }

    companion object {
        fun getInstance(context: Context) =
            Intent(context, ProfileActivity::class.java)
    }
}