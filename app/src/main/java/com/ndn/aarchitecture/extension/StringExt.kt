package com.ndn.aarchitecture.extension

import android.util.Patterns
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.regex.Pattern

@Throws(ParseException::class)
fun String.toDate(format: String): Date {
    val parser = SimpleDateFormat(format, Locale.getDefault())
    return parser.parse(this)
}

fun String?.isBlank(): Boolean {
    return this == null || isEmpty()
}

@Throws(ParseException::class)
fun String.toDateWithFormat(inputFormat: String, outputFormat: String): String {
    val gmtTimeZone = TimeZone.getTimeZone("UTC")
    val inputDateTimeFormat = SimpleDateFormat(inputFormat, Locale.getDefault())
    inputDateTimeFormat.timeZone = gmtTimeZone

    val outputDateTimeFormat = SimpleDateFormat(outputFormat, Locale.getDefault())
    outputDateTimeFormat.timeZone = gmtTimeZone
    return outputDateTimeFormat.format(inputDateTimeFormat.parse(this))
}

@Throws(ParseException::class)
fun String.toDateWithFormat(
    inputFormat: String,
    outputFormat: String,
    outputTimeZone: TimeZone = TimeZone.getDefault()
): String {
    val gmtTimeZone = TimeZone.getTimeZone("UTC")
    val inputDateTimeFormat = SimpleDateFormat(inputFormat, Locale.getDefault())
    inputDateTimeFormat.timeZone = gmtTimeZone

    val outputDateTimeFormat = SimpleDateFormat(outputFormat, Locale.getDefault())
    outputDateTimeFormat.timeZone = outputTimeZone
    return outputDateTimeFormat.format(inputDateTimeFormat.parse(this))
}

fun String.validWithPattern(pattern: Pattern): Boolean {
    return pattern.matcher(toLowerCase()).find()
}

fun String.validWithPattern(regex: String): Boolean {
    return Pattern.compile(regex).matcher(this).find()
}

fun List<String>.toStringWithFormatPattern(format: String): String {
    if (this.isEmpty()) {
        return ""
    }
    val builder = StringBuilder()
    for (s in this) {
        builder.append(s)
        builder.append(format)
    }
    var result = builder.toString()
    result = result.substring(0, result.length - format.length)
    return result
}

fun String.removeWhitespaces(): String {
    return this.replace("[\\s-]*".toRegex(), "")
}

fun String.subString(beginInDex: Int, endIndex: Int): String {
    return this.substring(beginInDex, endIndex)
}

fun String.insert(index: Int, contentInsert: String): String {
    val builder = StringBuilder(this)
    builder.insert(index, contentInsert)
    return builder.toString()
}

fun List<String>.convertStringToListStringWithFormatPattern(format: String): String {
    if (this.isEmpty()) {
        return ""
    }
    val builder = StringBuilder()
    for (s in this) {
        builder.append(s)
        builder.append(format)
    }
    var result = builder.toString()
    result = result.substring(0, result.length - format.length)
    return result
}

fun String.toIntOrZero() = if (this.toIntOrNull() == null) 0 else this.toInt()

fun String.isNumeric(): Boolean = this matches "-?\\d+(\\.\\d+)?".toRegex()

fun String.containsWebUrl() = Patterns.WEB_URL.matcher(this).find()

fun String?.nullToEmpty(): String = this ?: ""

fun String.splitString(maxLength: Int = 5): String {
    if (this.isBlank()) {
        return ""
    }

    var stringToFormat = this
    if (stringToFormat.length > maxLength) {
        stringToFormat = stringToFormat.substring(0, maxLength) + "..."
    }
    return stringToFormat
}
