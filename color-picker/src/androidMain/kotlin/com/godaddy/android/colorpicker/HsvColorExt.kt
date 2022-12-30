// ktlint-disable filename
package com.godaddy.android.colorpicker

/**
 * Convert HsvColor to Android Color Int
 *
 * returns @ColorInt
 */
fun HsvColor.toColorInt(): Int {
    return android.graphics.Color.HSVToColor((alpha * 255).toInt(), floatArrayOf(hue, saturation, value))
}
