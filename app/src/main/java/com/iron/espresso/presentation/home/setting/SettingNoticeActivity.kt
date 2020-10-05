package com.iron.espresso.presentation.home.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.iron.espresso.R
import com.iron.espresso.databinding.ActivitySettingContactBinding

class SettingNoticeActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting_notice)
    }
}