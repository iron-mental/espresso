package com.iron.espresso.presentation.home.setting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.fragment.app.setFragmentResultListener
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.databinding.ActivityDeleteUserBinding
import com.iron.espresso.ext.EventObserver
import com.iron.espresso.ext.onEditorEnterAction
import com.iron.espresso.ext.toast
import com.iron.espresso.presentation.home.apply.ConfirmDialog
import com.iron.espresso.presentation.sign.IntroActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteUserActivity : BaseActivity<ActivityDeleteUserBinding>(R.layout.activity_delete_user) {

    private val deleteUserViewModel by viewModels<DeleteUserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setToolbarTitle(R.string.membership_withdrawal)
        setNavigationIcon(R.drawable.ic_back_24)

        setupView()
        setupViewModel()
    }

    private fun setupView() {
        with(binding) {
            emailInputView.requestFocus()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)

            passwordInputView.onEditorEnterAction {
                showDeleteUserDialog(emailInputView.text.toString(), passwordInputView.text.toString())
            }
            confirmButton.setOnClickListener {
                showDeleteUserDialog(emailInputView.text.toString(), passwordInputView.text.toString())
            }
        }
    }

    private fun setupViewModel() {
        deleteUserViewModel.run {
            toastMessage.observe(this@DeleteUserActivity, EventObserver(::toast))

            successEvent.observe(this@DeleteUserActivity, EventObserver {
                startActivity(
                    IntroActivity.getIntent(this@DeleteUserActivity)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                )
            })
        }
    }

    private fun showDeleteUserDialog(email: String, password: String) {
        val dialog = ConfirmDialog.newInstance(getString(R.string.dialog_delete_user_title))

        dialog.show(supportFragmentManager, dialog::class.java.simpleName)

        dialog.setFragmentResultListener(dialog::class.java.simpleName) { _: String, bundle: Bundle ->
            val result = bundle.get(ConfirmDialog.RESULT)

            if (result == RESULT_OK) {
                deleteUserViewModel.membershipWithdrawal(email, password)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun getIntent(context: Context) =
            Intent(context, DeleteUserActivity::class.java)
    }
}