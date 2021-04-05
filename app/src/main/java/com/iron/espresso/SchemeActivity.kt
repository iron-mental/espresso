package com.iron.espresso

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.iron.espresso.di.ApiModule
import com.iron.espresso.domain.entity.AlertType
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.presentation.home.alert.AlertGateway
import io.reactivex.rxjava3.disposables.Disposable

class SchemeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val alertType = AlertType.getAlertType(intent.getStringExtra("pushEvent").orEmpty())

        val studyId = intent.getStringExtra("study_id").orEmpty().toIntOrNull()
        val alertId = intent.getStringExtra("alert_id").orEmpty().toIntOrNull()

        if (alertType != null && studyId != null && alertId != null) {
            var disposable: Disposable? = null
            disposable = ApiModule.provideAlertApi().readAlert(alertId = alertId)
                .networkSchedulers()
                .subscribe({
                    disposable?.dispose()
                }, {
                    disposable?.dispose()
                    Logger.e("${it.message}")
                })

            AlertGateway.goToPageByPush(this, alertType, studyId, alertId)
            finish()
        }
    }
}