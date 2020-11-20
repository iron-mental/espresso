package com.iron.espresso.di

import com.iron.espresso.presentation.home.mystudy.MyStudyViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MyStudyViewModel() }
}