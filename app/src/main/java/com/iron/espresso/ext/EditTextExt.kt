package com.iron.espresso.ext

import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.databinding.BindingAdapter

@BindingAdapter("bind:onEditorEnterAction")
fun EditText.onEditorEnterAction(f: (email: String) -> Unit?) {

    setOnEditorActionListener { v, actionId, event ->

        val imeAction = when (actionId) {
            EditorInfo.IME_ACTION_DONE,
            EditorInfo.IME_ACTION_SEARCH,
            EditorInfo.IME_ACTION_SEND,
            EditorInfo.IME_ACTION_NEXT,
            EditorInfo.IME_ACTION_GO -> true
            else -> false
        }

        val keyDownEvent = event?.keyCode == KeyEvent.KEYCODE_ENTER
                || event?.action == KeyEvent.ACTION_DOWN

        if (imeAction or keyDownEvent)
            true.also { f(v.editableText.toString()) }
        else false
    }
}