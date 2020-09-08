package com.iron.espresso.presentation.sign

import android.os.Bundle
import com.iron.espresso.R
import com.iron.espresso.base.DataBindingActivity
import com.iron.espresso.databinding.ActivityIntroBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class IntroActivity : DataBindingActivity() {

    private val binding: ActivityIntroBinding by binding(R.layout.activity_intro)
    private val introViewModel by viewModel<IntroViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.apply {
            lifecycleOwner = this@IntroActivity
            vm = introViewModel
        }
    }
}