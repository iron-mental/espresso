package com.iron.espresso.presentation.home.mystudy.studydetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.databinding.ActivityMemberProfileBinding

class MemberProfileActivity : BaseActivity<ActivityMemberProfileBinding>(R.layout.activity_member_profile) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setToolbarTitle("프로필")
        setNavigationIcon(R.drawable.ic_back_24)
    }

    companion object {
        fun getIntent(context: Context) =
            Intent(context, MemberProfileActivity::class.java)
    }
}