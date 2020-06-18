package com.ndn.aarchitecture.extension

import android.content.Context

/**
 * Copyright © 2020 Neolab VN.
 * Created by NguyenDan on 2020-03-17.
 */

fun Context.dpFromPx(px: Float): Float {
    return px / this.resources.displayMetrics.density
}

fun Context.pxFromDp(dp: Float): Float {
    return dp * this.resources.displayMetrics.density
}
