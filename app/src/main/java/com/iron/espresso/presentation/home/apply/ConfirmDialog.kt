package com.iron.espresso.presentation.home.apply

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.iron.espresso.R
import com.iron.espresso.base.BaseDialogFragment
import com.iron.espresso.databinding.DialogConfirmBinding

class ConfirmDialog : BaseDialogFragment<DialogConfirmBinding>(R.layout.dialog_confirm) {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
    }

    private fun setupView() {
        with(binding) {
            arguments?.run {
                setTitle(getString(KEY_TITLE, ""))
                setSubTitle(getString(KEY_SUB_TITLE, ""))
                cancelBtnName = getString(KEY_CANCEL_BTN_NAME) ?: getString(R.string.no)
                okBtnName = getString(KEY_OK_BTN_NAME) ?: getString(R.string.yes)
            }

            yes.setOnClickListener {
                setFragmentResult(
                    this@ConfirmDialog::class.java.simpleName,
                    bundleOf(RESULT to Activity.RESULT_OK)
                )
                dismiss()
            }

            no.setOnClickListener {
                setFragmentResult(
                    this@ConfirmDialog::class.java.simpleName,
                    bundleOf(RESULT to Activity.RESULT_CANCELED)
                )
                dismiss()
            }
        }
    }

    companion object {
        const val RESULT = "result"

        private const val KEY_TITLE = "TITLE"
        private const val KEY_SUB_TITLE = "SUB_TITLE"
        private const val KEY_CANCEL_BTN_NAME = "CANCEL_BTN_NAME"
        private const val KEY_OK_BTN_NAME = "OK_BTN_NAME"

        fun newInstance(
            title: String,
            subTitle: String = "",
            cancelBtnName: String? = null,
            okBtnName: String? = null
        ) =
            ConfirmDialog().apply {
                arguments = Bundle().apply {
                    putString(KEY_TITLE, title)
                    putString(KEY_SUB_TITLE, subTitle)
                    putString(KEY_CANCEL_BTN_NAME, cancelBtnName)
                    putString(KEY_OK_BTN_NAME, okBtnName)
                }
            }
    }
}