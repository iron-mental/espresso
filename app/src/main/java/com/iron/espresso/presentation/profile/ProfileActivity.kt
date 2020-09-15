package com.iron.espresso.presentation.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
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


        //주세용
        binding.btnGithub.setOnClickListener {
            val userId = binding.edtGithubId.text.toString()
            viewModel.getProfileImage(userId)
        }

        //받음
        viewModel.avatarUrl.observe(this, Observer { avatarUrl ->
            Glide.with(this)
                .load(avatarUrl)
                .into(binding.profileImage)
        })
    }

    companion object {
        fun getInstance(context: Context) =
            Intent(context, ProfileActivity::class.java)
    }
}