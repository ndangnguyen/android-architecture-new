package com.ndn.aarchitecture.extension

import java.text.DecimalFormat

fun Int?.nullToDefault() = this ?: -1

fun Int?.nullToZero() = this ?: 0

fun Int?.isTrue() = this == 1

fun Double?.isNullOrZero() = this == 0.0 || this == null

fun Double?.nullToDefault() = this ?: -1.0

fun Double?.nullToZero() = this ?: 0.0

fun Double.formatMoney(): String {
    val formatter = DecimalFormat("###,###,###")
    return formatter.format(this)
}
