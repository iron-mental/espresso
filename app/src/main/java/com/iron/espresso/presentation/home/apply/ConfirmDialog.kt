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

        fun newInstance(title: String, subTitle: String = "") =
            ConfirmDialog().apply {
                arguments = bundleOf(
                    KEY_TITLE to title,
                    KEY_SUB_TITLE to subTitle
                )
            }
    }
}