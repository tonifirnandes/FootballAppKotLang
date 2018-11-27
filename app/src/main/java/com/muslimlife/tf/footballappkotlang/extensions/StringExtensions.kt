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
        return null
    }
}

fun String.adjustTimePatternWithTimezone(oldPattern: String, newPattern: String, timeZoneId: String): String? {
    val dateFormat = SimpleDateFormat(oldPattern, Locale.getDefault())
    return try {
        val calendar = Calendar.getInstance()
        calendar.timeZone = TimeZone.getTimeZone(timeZoneId)
        calendar.time = dateFormat.parse(this)
        dateFormat.applyPattern(newPattern)
        dateFormat.format(calendar.time)
    } catch (e: ParseException) {
        return null
    }
}

fun String.split(): String {
    return this.replace("; ", ";").replace(";", "\n")
}