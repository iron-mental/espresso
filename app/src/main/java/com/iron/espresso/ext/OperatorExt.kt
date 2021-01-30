package com.iron.espresso.ext

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

operator fun CompositeDisposable.plusAssign(subscribe: Disposable) {
    add(subscribe)
}

inline fun <reified T> Single<T>.networkSchedulers(): Single<T> =
    subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

fun Completable.networkSchedulers(): Completable =
    subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())