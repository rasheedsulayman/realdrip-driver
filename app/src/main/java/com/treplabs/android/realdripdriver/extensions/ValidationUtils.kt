package com.treplabs.android.realdripdriver.extensions

import android.text.TextUtils
import android.util.Patterns
import android.widget.EditText
import android.widget.TextView

fun validateTextLayouts(vararg textLayouts: TextView): Boolean {
    clearTextLayoutError(*textLayouts)
    for (textLayout in textLayouts) {
        if (!textLayout.validate()) return false
    }
    return true
}

fun clearTextLayoutError(vararg textLayouts: TextView) {
    for (textLayout in textLayouts) textLayout.error = null
}

fun EditText.validateEmail(): Boolean {
    if (!TextUtils.isEmpty(text) && Patterns.EMAIL_ADDRESS.matcher(text).matches()) return true
    error = "Invalid Email Address"
    return false
}

fun EditText.hasContent(): Boolean {
    if (!TextUtils.isEmpty(text)) return true
    error = "This Field is required"
    return false
}
