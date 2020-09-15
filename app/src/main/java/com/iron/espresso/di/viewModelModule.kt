package com.iron.espresso.di

import com.iron.espresso.domain.usecase.GetUser
import com.iron.espresso.model.repo.ProfileRepositoryImpl
import com.iron.espresso.presentation.viewmodel.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ProfileViewModel(GetUser(ProfileRepositoryImpl.getInstance())) }
}