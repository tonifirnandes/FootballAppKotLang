package com.muslimlife.tf.footballappkotlang.extensions

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import java.text.ParseException

class StringExtensionsKtTest {

    @Test
    fun adjustTimePattern_isValid() {
        val originString = "2018-11-11"
        assertEquals(
            "Sun, 11 Nov 2018",
            originString.adjustTimePattern(
                GenericDateFormatConstant.originEventDateTimeFormat,
                GenericDateFormatConstant.matchEventDateTimeFormat
            )
        )
    }

    @Test
    fun adjustTimePattern_isWrongOriginDateValue() {
        val originString = "11-11-2018"
        assertNotEquals(
            "Sun, 11 Nov 2018",
            originString.adjustTimePattern(
                GenericDateFormatConstant.originEventDateTimeFormat,
                GenericDateFormatConstant.matchEventDateTimeFormat
            )
        )
    }

    @Test(expected = ParseException::class)
    fun adjustTimePattern_isInvalidOriginDateFormat() {
        val originString = "11/11/2018"
        assertEquals(
            "Sun, 11 Nov 2018",
            originString.adjustTimePattern(
                GenericDateFormatConstant.originEventDateTimeFormat,
                GenericDateFormatConstant.matchEventDateTimeFormat
            )
        )
    }

}