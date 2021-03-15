package com.iron.espresso.presentation.home.alert

import android.content.Context
import android.widget.Toast
import com.iron.espresso.R
import com.iron.espresso.domain.entity.AlertType
import com.iron.espresso.presentation.home.apply.ApplyStudyActivity
import com.iron.espresso.presentation.home.mystudy.MyStudyDetailActivity

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
                Toast.makeText(context, context.getString(R.string.toast_study_deleted, studyTitle), Toast.LENGTH_SHORT).show()
            }
            AlertType.APPLY_ALLOW -> {
                context.startActivity(MyStudyDetailActivity.getInstance(context, id = studyId))
            }
            AlertType.APPLY_REJECT -> {
                Toast.makeText(context, context.getString(R.string.toast_study_rejected, studyTitle), Toast.LENGTH_SHORT).show()
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


    fun goToPageByPush(context: Context, alertType: AlertType, studyId: Int, alertId: Int) {
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
                context.startActivity(AlertListActivity.getIntent(context))
            }
            AlertType.APPLY_ALLOW -> {
                context.startActivity(MyStudyDetailActivity.getInstance(context, id = studyId))
            }
            AlertType.APPLY_REJECT -> {
                context.startActivity(AlertListActivity.getIntent(context))
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
                context.startActivity(AlertListActivity.getIntent(context))
            }
        }
    }
}