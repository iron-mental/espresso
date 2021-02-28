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

class ApplyStudyDialog :
    BaseDialogFragment<DialogApplyStudyBinding>(R.layout.dialog_apply_study) {

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
            inputIntroduce.requestFocus()

            arguments?.run {
                setTitle(getString(KEY_TITLE, ""))
                introduce = getString(KEY_INTRODUCE, "")
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

            no.setOnClickListener {
                dismiss()
            }
        }
    }

    companion object {
        const val SUBMIT = "submit"
        const val MESSAGE = "message"

        private const val KEY_TITLE = "TITLE"
        private const val KEY_INTRODUCE = "INTRODUCE"

        fun newInstance(title: String, introduce: String = "") =
            ApplyStudyDialog().apply {
                arguments = bundleOf(
                    KEY_TITLE to title,
                    KEY_INTRODUCE to introduce
                )
            }
    }
}