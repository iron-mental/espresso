package com.iron.espresso.presentation.home.apply

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.iron.espresso.R
import com.iron.espresso.base.BaseDialogFragment
import com.iron.espresso.databinding.DialogApplyStudyBinding
import com.iron.espresso.ext.toast

class ApplyStudyDialog : BaseDialogFragment<DialogApplyStudyBinding>(R.layout.dialog_apply_study) {

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
            no.setOnClickListener {
                dismiss()
            }

            yes.setOnClickListener {
                val message = binding.inputIntroduce.text.toString()

                if (message.isNotEmpty()) {
                    binding.inputIntroduce.text.clear()

                    val data = bundleOf(MESSAGE to message)
                    setFragmentResult(SUBMIT, data)
                    dismiss()
                } else {
                    toast(R.string.toast_empty_introduce)
                }
            }
        }
    }

    companion object {
        const val SUBMIT = "submit"
        const val MESSAGE = "message"

        fun newInstance() =
            ApplyStudyDialog()
    }
}