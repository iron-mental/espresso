package com.iron.espresso.custom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View.OnTouchListener
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat
import com.iron.espresso.R

class EditTextWithClear : AppCompatEditText {

    private lateinit var clearDrawable: Drawable

    constructor(context: Context) : super(context) {
        operateCustom()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        operateCustom()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        operateCustom()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun operateCustom() {
        setClearIconVisible(false)

        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                setClearIconVisible(s.isNotEmpty())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        setOnTouchListener(OnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (right - compoundPaddingRight)) {
                    text?.clear()
                    return@OnTouchListener true
                }
            }
            return@OnTouchListener false
        })
    }

    private fun setClearIcon() {
        clearDrawable = ResourcesCompat.getDrawable(
            resources,
            R.drawable.ic_clear, null
        )!!
    }


    private fun setClearIconVisible(visible: Boolean) {
        setClearIcon()
        setCompoundDrawablesRelativeWithIntrinsicBounds(
            null,
            null,
            if (visible) clearDrawable else null,
            null
        )
    }
}