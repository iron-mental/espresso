package com.iron.espresso.presentation.home.alert

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.databinding.ActivityAlertListBinding
import com.iron.espresso.ext.dp
import com.iron.espresso.presentation.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlertListActivity : BaseActivity<ActivityAlertListBinding>(R.layout.activity_alert_list) {

    private val viewModel by viewModels<AlertListViewModel>()

    private val adapter: AlertListAdapter by lazy {
        AlertListAdapter { item ->
            if (!item.confirm) {
                viewModel.read(alertId = item.id)
            }
            item.alertType?.let { type ->
                AlertGateway.goToPage(this, type, item.studyId, item.studyTitle)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setToolbarTitle(R.string.alert)
        setNavigationIcon(R.drawable.ic_back_24)

        setupView()
        setupViewModel()
        viewModel.getList()
    }

    private fun setupView() {
        with(binding) {
            alertList.adapter = adapter

            alertList.addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    super.getItemOffsets(outRect, view, parent, state)

                    outRect.bottom = 10.dp
                }
            })

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupViewModel() {
        with(viewModel) {
            alertList.observe(this@AlertListActivity, { list ->
                adapter.submitList(list)
            })
        }
    }

    override fun onBackPressed() {
        if (isTaskRoot) {
            startActivity(HomeActivity.getIntent(this))
        }
        super.onBackPressed()
    }

    companion object {
        fun getIntent(context: Context) =
            Intent(context, AlertListActivity::class.java)
    }
}