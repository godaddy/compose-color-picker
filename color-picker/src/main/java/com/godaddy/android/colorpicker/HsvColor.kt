package com.godaddy.android.colorpicker

import android.os.Parcelable
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ExperimentalGraphicsApi
import androidx.compose.ui.graphics.toArgb
import kotlinx.parcelize.Parcelize

/**
 * A representation of Color in Hue, Saturation and Value form.
 */
@Parcelize
data class HsvColor(

    @FloatRange(from = 0.0, to = 360.0)
    val hue: Float,

    @FloatRange(from = 0.0, to = 1.0)
    val saturation: Float,

    @FloatRange(from = 0.0, to = 1.0)
    val value: Float,

    @FloatRange(from = 0.0, to = 1.0)
    val alpha: Float
) : Parcelable {

    @ExperimentalGraphicsApi
    fun toColor(): Color {
        return Color.hsv(hue, saturation, value, alpha)
    }

    /**
     * Transforms HsvColor to Color Int value.
     */
    @ColorInt
    fun toColorInt(): Int {
        return android.graphics.Color.HSVToColor((alpha * 255).toInt(), floatArrayOf(hue, saturation, value))
    }

    companion object {

        val DEFAULT = HsvColor(360f, 1.0f, 1.0f, 1.0f)

        /**
         * Creates an HsvColor from Color Int
         */
        fun from(@ColorInt color: Int): HsvColor {
            val floatArray = FloatArray(3)
            android.graphics.Color.colorToHSV(color, floatArray)
            return HsvColor(hue = floatArray[0],
                saturation = floatArray[1],
                value = floatArray[2],
                alpha = android.graphics.Color.alpha(color) / 255f)
        }
        /**
         * Creates an HsvColor from Color Int
         */
        fun from(@ColorInt color: Color): HsvColor {
            val floatArray = FloatArray(3)
            val argb = color.toArgb()
            android.graphics.Color.colorToHSV(argb, floatArray)
            return HsvColor(hue = floatArray[0],
                saturation = floatArray[1],
                value = floatArray[2],
                alpha = color.alpha)
        }
    }
}
