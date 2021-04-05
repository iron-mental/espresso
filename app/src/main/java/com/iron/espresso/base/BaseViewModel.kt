package com.iron.espresso.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iron.espresso.ext.Event
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {
    protected val compositeDisposable = CompositeDisposable()

    protected val _toastMessage = MutableLiveData<Event<String>>()
    val toastMessage: LiveData<Event<String>> get() = _toastMessage

    private val _loadingState = MutableLiveData<Event<Boolean>>()
    val loadingState: LiveData<Event<Boolean>> get() = _loadingState

    protected fun showLoading() {
        _loadingState.value = Event(true)
    }

    protected fun hideLoading() {
        _loadingState.value = Event(false)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}