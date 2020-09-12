package com.iron.espresso.di

import com.iron.espresso.presentation.sign.IntroViewModel
import com.iron.espresso.presentation.sign.SignInViewModel
import com.iron.espresso.presentation.sign.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { IntroViewModel() }
    viewModel { SignUpViewModel() }
    viewModel { SignInViewModel() }
}