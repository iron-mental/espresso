package com.iron.espresso.ext

import android.widget.TextView

fun TextView.setTextColorIf(defaultColor: Int, changeColor: Int, setDefault: Boolean) {
    this.setTextColor(if (setDefault) defaultColor else changeColor)
}