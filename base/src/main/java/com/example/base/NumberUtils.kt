package com.example.base

import android.content.res.Resources
import android.util.TypedValue

/**
 * dp转px
 */
fun Float.dp2Px(): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )
}