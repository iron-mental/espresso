package com.iron.espresso.presentation.home.setting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.databinding.ActivityDeleteUserBinding

class DeleteUserActivity : BaseActivity<ActivityDeleteUserBinding>(R.layout.activity_delete_user) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    companion object {
        fun getIntent(context: Context) =
            Intent(context, DeleteUserActivity::class.java)
    }
}