package com.iron.espresso

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentLoadingBinding
import com.iron.espresso.ext.plusAssign
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class LoadingFragment : BaseFragment<FragmentLoadingBinding>(R.layout.fragment_loading) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        compositeDisposable += Single.just(0)
            .delay(100, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                binding.loadingView.isVisible = true
            }, {

            })
    }

    companion object {
        fun newInstance() =
            LoadingFragment()
    }
}