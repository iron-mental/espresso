package com.iron.espresso.presentation.home.alert

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.databinding.ActivityAlertListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlertListActivity : BaseActivity<ActivityAlertListBinding>(R.layout.activity_alert_list) {

    private val viewModel by viewModels<AlertListViewModel>()

    private val adapter: AlertListAdapter by lazy {
        AlertListAdapter {
            viewModel.read(alertId = it.id)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setToolbarTitle(R.string.alert)

        setupView()
        setupViewModel()
        viewModel.getList()
    }

    private fun setupView() {
        with(binding) {
            alertList.adapter = adapter
        }
    }

    private fun setupViewModel() {
        with(viewModel) {
            alertList.observe(this@AlertListActivity, { list ->
                adapter.submitList(list)
            })
        }
    }

    companion object {
        fun getIntent(context: Context) =
            Intent(context, AlertListActivity::class.java)
    }
}