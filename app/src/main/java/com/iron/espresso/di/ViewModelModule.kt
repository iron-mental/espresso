package com.iron.espresso.di

import com.iron.espresso.presentation.home.mystudy.MyStudyViewModel
import com.iron.espresso.presentation.sign.IntroViewModel
import com.iron.espresso.presentation.viewmodel.SignInViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { IntroViewModel() }
//    viewModel { SignUpViewModel(get()) }
    viewModel { MyStudyViewModel() }
    viewModel { SignInViewModel(get()) }
}