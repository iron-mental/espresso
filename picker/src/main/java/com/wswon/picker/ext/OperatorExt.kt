package com.wswon.picker.ext

import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable


operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
    add(disposable)
}