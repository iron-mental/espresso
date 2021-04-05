package com.iron.espresso.ext

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

operator fun CompositeDisposable.plusAssign(subscribe: Disposable) {
    add(subscribe)
}

inline fun <reified T> Single<T>.networkSchedulers(): Single<T> =
    subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

fun Completable.networkSchedulers(): Completable =
    subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())