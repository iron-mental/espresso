package com.iron.espresso.view

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.iron.espresso.R

class EmptyListView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.view_empty_list, this)

        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.EmptyListView)
            findViewById<AppCompatImageView>(R.id.icon)
                .setImageDrawable(a.getDrawable(R.styleable.EmptyListView_image))
            findViewById<TextView>(R.id.message).text =
                a.getString(R.styleable.EmptyListView_message).orEmpty()

            a.recycle()
        }
    }
}