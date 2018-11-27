package com.muslimlife.tf.footballappkotlang.extensions

class GenericDateFormatConstant {
    companion object {
        const val originEventDateTimeFormat: String = "yyyy-MM-dd" //2018-11-10
        const val matchEventDateTimeFormat: String = "EEE, dd MMM yyyy"
        const val originEventDateTimeFormatWithTimeZone: String = "yyyy-MM-dd HH:mm:ssZZZZZ" //"strTime": "20:00:00+00:00",
        const val matchEventTimeFormat: String = "HH:mm"
        const val timeZoneGMT7: String = "Asia/Jakarta"
    }
}
