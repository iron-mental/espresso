package com.iron.espresso.presentation.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.iron.espresso.MenuSet
import com.iron.espresso.R
import com.iron.espresso.databinding.ActivityProfileBinding
import com.iron.espresso.model.repo.ProfileRepositoryImpl
import com.iron.espresso.presentation.viewmodel.ProfileViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    private val viewModel by viewModel<ProfileViewModel> { parametersOf(ProfileRepositoryImpl.getInstance()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        binding.lifecycleOwner = this

        setSupportActionBar(binding.toolbar)
        supportActionBar?.run {
            setTitle(R.string.profile_title)
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_back_24)
        }

        binding.btnGithub.setOnClickListener {
            val userId = binding.edtGithubId.text.toString()
            viewModel.getProfileImage(userId)
        }

        viewModel.avatarUrl.observe(this, Observer { avatarUrl ->
            Glide.with(this)
                .load(avatarUrl)
                .into(binding.profileImage)
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