package com.iron.espresso

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.setFragmentResultListener
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.databinding.ActivitySplashBinding
import com.iron.espresso.ext.EventObserver
import com.iron.espresso.presentation.home.HomeActivity
import com.iron.espresso.presentation.home.apply.ConfirmDialog
import com.iron.espresso.presentation.sign.IntroActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    private val viewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(viewModel) {
            userInfoResult.observe(this@SplashActivity, { userResponse ->
                UserHolder.set(userResponse)
                goHomeActivity()
            })

            failedEvent.observe(this@SplashActivity, EventObserver {
                goIntroActivity()
            })
            updateEvent.observe(this@SplashActivity, EventObserver { updateType ->
                if (updateType == SplashViewModel.UpdateType.SERVER_CLOSED) {
                    showServerClosedDialog()
                } else {
                    showUpdateDialog(updateType)
                }
            })
        }
    }

    private fun showServerClosedDialog() {
        val dialog = ConfirmDialog.newInstance(
            title = getString(R.string.dialog_server_closed_title),
            subTitle = getString(R.string.dialog_server_closed_sub_title),
            cancelBtnName = "",
            okBtnName = getString(R.string.close)
        )

        dialog.show(supportFragmentManager, dialog::class.java.simpleName)

        dialog.setFragmentResultListener(dialog::class.java.simpleName) { _: String, bundle: Bundle ->
            when (bundle.get(ConfirmDialog.RESULT)) {
                Activity.RESULT_OK -> {
                    finish()
                }
            }
        }
    }

    private fun showUpdateDialog(updateType: SplashViewModel.UpdateType) {
        val dialog = ConfirmDialog.newInstance(
            title = getString(R.string.dialog_update_title),
            subTitle = getString(R.string.dialog_update_sub_title),
            cancelBtnName = if (updateType != SplashViewModel.UpdateType.REQUIRED) getString(R.string.later) else "",
            okBtnName = getString(R.string.update)
        )

        dialog.show(supportFragmentManager, dialog::class.java.simpleName)

        dialog.setFragmentResultListener(dialog::class.java.simpleName) { _: String, bundle: Bundle ->
            when (bundle.get(ConfirmDialog.RESULT)) {
                Activity.RESULT_OK -> {
                    val intent = Intent(Intent.ACTION_VIEW)
                        .setData(Uri.parse("market://details?id=" + BuildConfig.APPLICATION_ID))

                    startActivity(intent)
                }

                Activity.RESULT_CANCELED -> {
                    val userId = AuthHolder.requireId()
                    if (userId != -1) {
                        viewModel.checkTokenAndResult(userId)
                    } else {
                        goIntroActivity()
                    }
                }
            }
        }
    }

    private fun goIntroActivity() {
        startActivity(IntroActivity.getIntent(this).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
        finish()
    }

    private fun goHomeActivity() {
        startActivity(HomeActivity.getIntent(this))
        finish()
    }
}

