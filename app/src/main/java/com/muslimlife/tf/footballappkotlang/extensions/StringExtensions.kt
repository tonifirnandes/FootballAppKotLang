package com.muslimlife.tf.footballappkotlang.extensions

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun String.adjustTimePattern(oldPattern: String, newPattern: String): String? {
    val dateFormat = SimpleDateFormat(oldPattern, Locale.getDefault())
    return try {
        val calendar = Calendar.getInstance()
        calendar.time = dateFormat.parse(this)
        dateFormat.applyPattern(newPattern)
        dateFormat.format(calendar.time)
    } catch (e: ParseException) {
        throw(e)
    }
}

fun String.splitted(): String {
    return this.replace("; ", ";").replace(";", "\n")
}