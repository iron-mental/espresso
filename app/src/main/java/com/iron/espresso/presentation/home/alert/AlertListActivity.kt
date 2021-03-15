package com.iron.espresso.presentation.home.alert

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.databinding.ActivityAlertListBinding
import com.iron.espresso.domain.entity.AlertType
import com.iron.espresso.ext.dp
import com.iron.espresso.presentation.home.apply.ApplyStudyActivity
import com.iron.espresso.presentation.home.mystudy.MyStudyDetailActivity
import dagger.hilt.android.AndroidEntryPoint

object AlertGateway {

    fun goToPage(context: Context, alertType: AlertType, studyId: Int, studyTitle: String = "") {
        when (alertType) {
            AlertType.APPLY_NEW -> {
                context.startActivity(ApplyStudyActivity.getIntent(context, studyId = studyId))
            }
            AlertType.STUDY_UPDATE -> {
                context.startActivity(MyStudyDetailActivity.getInstance(context, id = studyId))
            }
            AlertType.STUDY_DELEGATE -> {
                context.startActivity(MyStudyDetailActivity.getInstance(context, id = studyId))
            }
            AlertType.STUDY_DELETE -> {
                Toast.makeText(context, "$studyTitle 스터디가 삭제되었습니다.", Toast.LENGTH_SHORT).show()
            }
            AlertType.APPLY_ALLOW -> {
                context.startActivity(MyStudyDetailActivity.getInstance(context, id = studyId))
            }
            AlertType.APPLY_REJECT -> {
                Toast.makeText(context, "$studyTitle 스터디 입장이 거절되었습니다.", Toast.LENGTH_SHORT).show()
            }
            AlertType.NOTICE_NEW -> {
                context.startActivity(MyStudyDetailActivity.getInstance(context, id = studyId))
            }
            AlertType.NOTICE_UPDATE -> {
                context.startActivity(MyStudyDetailActivity.getInstance(context, id = studyId))
            }
            AlertType.CHAT -> {
            }
            AlertType.PUSH_TEST -> {
            }
        }
    }
}


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

    companion object {
        fun getIntent(context: Context) =
            Intent(context, AlertListActivity::class.java)
    }
}