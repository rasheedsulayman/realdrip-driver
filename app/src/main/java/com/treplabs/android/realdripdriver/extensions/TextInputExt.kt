package com.treplabs.android.realdripdriver.extensions

import android.widget.EditText
import android.widget.TextView

fun TextView.isEmpty() = text.isNullOrEmpty()

fun TextView.validate(errorMessage: String = "This Field is required"): Boolean {
    if (!isEmpty()) return true
    error = errorMessage
    return false
}

fun EditText.stringContent(): String = text.toString()
