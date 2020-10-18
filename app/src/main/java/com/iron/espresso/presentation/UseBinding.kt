package com.iron.espresso.presentation

import androidx.databinding.ViewDataBinding

interface UseBinding<T: ViewDataBinding> {
    val binding: T
}